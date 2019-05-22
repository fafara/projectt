package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nexgo.oaf.apiv3.APIProxy;
import com.nexgo.oaf.apiv3.DeviceEngine;
import com.nexgo.oaf.apiv3.card.cpu.CPUCardHandler;
import com.nexgo.oaf.apiv3.device.printer.AlignEnum;
import com.nexgo.oaf.apiv3.device.printer.OnPrintListener;
import com.nexgo.oaf.apiv3.device.printer.Printer;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Panier extends AppCompatActivity {

    DataBase mydb;
    ListView liste;
    ImageView supprimer;
    int sum = 0;
    TextView PrixTotal;

    Button valider, retour;
    ProdCommandéListAdapter adapter = null;
    ArrayList<ProduitCommandé> list;


    String datetime;
    String datetimeTicket;
    String designation = "";
    String quantité = "";
    float prixTTCC;
    String ttc ="";

    public DeviceEngine deviceEngine;
    public CPUCardHandler cpuCardHandler;
    Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        valider = (Button) findViewById(R.id.validerpanier);
        supprimer = (ImageView) findViewById(R.id.supprimerachat);
        // quantité = (TextView) findViewById(R.id.qte);
        //designation = (TextView) findViewById(R.id.des);
        //prix = (TextView) findViewById(R.id.ttc);
        retour = (Button) findViewById(R.id.textView40);
        PrixTotal = (TextView) findViewById(R.id.Ptotal);
        liste = findViewById(R.id.lv1);
        list = new ArrayList<>( );

        mydb = new DataBase(getApplicationContext( ));
        mydb.open( );

        retour.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Panier.this, ListeCategories.class);
                startActivity(intent);
            }
        });


        Cursor data = mydb.listeProdCommandé( );
        // liste.setAdapter(new ProdCommandéListAdapter(this, mydb.listeProdCommandé( )));


        if (data.getCount( ) == 0) {
            Toast.makeText(Panier.this, "Il n'existe aucun produit", Toast.LENGTH_SHORT).show( );
        } else {
            while (data.moveToNext( )) {

                designation = data.getString(4);
                quantité = String.valueOf(data.getInt(5));
                prixTTCC = data.getFloat(8);
                ttc = String.valueOf(data.getFloat(9));

                list.add(new ProduitCommandé(designation, quantité, prixTTCC, ttc));

            }

            // adapter.notifyDataSetChanged();
            adapter = new ProdCommandéListAdapter(this, R.layout.item_liste_panier, list);
            liste.setAdapter(adapter);


          /*/sum = mydb.GetMontantProduitsCommander( );


            quantité.set(position, m_Text.toString( ));
            listdata5.set(position, String.valueOf(newpoint));


            adapter.notifyDataSetChanged( );

            Totaltxt.setText("TOTAL POINTS : " + String.valueOf(sum) + " PTS");/*/


        }


        Calendar c = Calendar.getInstance( );
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        datetime = dateformat.format(c.getTime( ));

        SimpleDateFormat dateformat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        datetimeTicket = dateformat2.format(c.getTime( ));


        valider.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {


                deviceEngine = APIProxy.getDeviceEngine( );
                Printer printer = deviceEngine.getPrinter( );
                printer.initPrinter( );
                printer.setTypeface(Typeface.SANS_SERIF); //caractere


                //Print Logo

                Bitmap bmp = BitmapFactory.decodeResource(getResources( ), R.drawable.sss); //bitmap N,B monochrome
                printer.appendImage(bmp, AlignEnum.CENTER);


                //bold false

                printer.appendPrnStr("My store", 26, AlignEnum.CENTER, false);
                printer.appendPrnStr("Butterfly - Food and Drinks", 26, AlignEnum.CENTER, false);
                printer.appendPrnStr("14 rue Ahmed Tlili Menzah 5 Ariana", 26, AlignEnum.CENTER, false);
                printer.appendPrnStr(".-----------------------------.", 26, AlignEnum.CENTER, false);
                printer.appendPrnStr("Le :" + datetimeTicket, 26, AlignEnum.LEFT, false);
                printer.appendPrnStr("" + designation     +quantité     +ttc, 26, AlignEnum.LEFT, false);
                printer.appendPrnStr("ID Terminal: 9999901011", 26, AlignEnum.LEFT, false);
                printer.appendPrnStr("Opp: 00001223 01/06/2018", 26, AlignEnum.LEFT, false);

                printer.appendPrnStr("ACHAT", 32, AlignEnum.CENTER, true);
                printer.appendPrnStr(".----------.", 26, AlignEnum.CENTER, false);


                printer.appendPrnStr("Nouveau : 30,080 TND", 32, AlignEnum.LEFT, true);
                printer.feedPaper(5);
                printer.cutPaper( );

                printer.startPrint(true, new OnPrintListener( ) {
                    @Override
                    public void onPrintResult(final int retCode) {
                        runOnUiThread(new Runnable( ) {
                            @Override
                            public void run() {

                                Toast.makeText(mContext, retCode + " Impression", Toast.LENGTH_LONG).show( );
                            }
                        });
                    }
                });


            }
        });

    }
}
