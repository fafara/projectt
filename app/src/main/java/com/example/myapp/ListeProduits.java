package com.example.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ListeProduits extends AppCompatActivity {

    DataBase mydb;
    GridView gridView;
    ArrayList<Produit> liste;
    ProduitListAdapter adapter = null;
    EditText userInputDialogEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_liste_produits);

        gridView = (GridView) findViewById(R.id.gridview);
        liste = new ArrayList<>( );

        mydb = new DataBase(getApplicationContext( ));
        mydb.open( );


        Intent i = getIntent( );
        String pos1 = i.getStringExtra("position");
        Integer pos = Integer.valueOf(pos1);
        Cursor data = mydb.ListProd(pos);


        if (data.getCount( ) == 0) {
            Toast.makeText(ListeProduits.this, "Il n'existe aucun produit", Toast.LENGTH_SHORT).show( );
        } else {
            while (data.moveToNext( )) {

                String designation = data.getString(1);
                float prixTTCC = data.getFloat(4);
                byte[] gallerybutton = data.getBlob(5);

                liste.add(new Produit(designation, prixTTCC, gallerybutton));
            }

            // adapter.notifyDataSetChanged();
            adapter = new ProduitListAdapter(this, R.layout.item_liste_produit, liste);
            gridView.setAdapter(adapter);

        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener( ) {
            // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                final AppCompatActivity activity = (AppCompatActivity) view.getContext( );


                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(activity);
                View mView = layoutInflaterAndroid.inflate(R.layout.quantite, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(activity);
                alertDialogBuilderUserInput.setView(mView);


                alertDialogBuilderUserInput.setTitle("Choisir quantité");


                userInputDialogEditText = (EditText) mView.findViewById(R.id.display);
                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener( ) {
                    public void onClick(DialogInterface dialogBox, int id) {
                        // ToDo get user input here
                        String m_Text = userInputDialogEditText.getText( ).toString( );

                        if (m_Text.equals("0")) {

                            showDialogerror(activity, "Erreur Quantité inserer");
                        } else {


                            mydb = new DataBase(activity);
                            mydb.open( );


                            TextView des = findViewById(R.id.txtdes);
                            String designation = des.getText().toString();
                            String id_Prod= String.valueOf(mydb.selectIdProd(designation));


                                    List<Produit> listProdCmd = mydb.Select_ProduitInfosbyID(id_Prod);



                                    ProduitCommandé pc = new ProduitCommandé();

                                    pc.setIdCategorie(String.valueOf(listProdCmd.get(0).getIdCat()));
                                    pc.setNomCategorie(listProdCmd.get(0).getNomCat());
                                    pc.setIdProduit(String.valueOf(listProdCmd.get(0).getId_prod()));
                                    pc.setDesignation(listProdCmd.get(0).getDesignation());
                                    pc.setQuantite(m_Text);
                                    pc.setPrixHT(listProdCmd.get(0).getPrixHT());
                                    pc.setTva(listProdCmd.get(0).getTva());
                                    pc.setPrixTTC(listProdCmd.get(0).getPrixTTCC());


                                  //  int iptsclient = Integer.parseInt(m_Text) * Integer.parseInt(lstcad.get(0).getNbrepoint());

                                    //int iptsaffiliee = Integer.parseInt(m_Text) * Integer.parseInt(lstcad.get(0).getNbrepoint());

                                   // pc.setPts_client(String.valueOf(iptsclient));
                                   // pc.setPts_affilie(String.valueOf(iptsaffiliee));

                                    mydb.open();
                                    mydb.InsererProduitCommander(pc);


                            //InputMethodManager inputMethodManager = (InputMethodManager) view.getContext( ).getSystemService(Context.INPUT_METHOD_SERVICE);
                           //inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


                            showDialogconfirmed(activity, "Votre Produit est inseré avec succée");

                        }


                    }
                })

                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener( ) {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel( );
                            }
                        });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create( );
                alertDialogAndroid.show( );


            }
        });
    }


  /*/  public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        Log.i("value is",""+newVal);

    }/*/


 /*/   public void show()
    {

        final Dialog d = new Dialog(ListeProduits.this);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.quantite);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker numberPicker = (NumberPicker) d.findViewById(R.id.numberPicker1);
        numberPicker.setMaxValue(20); // max value 20
        numberPicker.setMinValue(0);   // min value 0
        //numberPicker.setWrapSelectorWheel(false);
       // numberPicker.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //tv.setText(String.valueOf(numberPicker.getValue())); //set the value to textview
                int m_Text = numberPicker.getValue();

                if(m_Text == 0){
                    Toast.makeText(ListeProduits.this, "Erreur !! ", Toast.LENGTH_SHORT).show( );

//                    showDialogerror((Activity) getApplicationContext(), "Erreur Quantité inserer");
                }else{






























                //Toast.makeText(ListeProduits.this, " choisit", Toast.LENGTH_SHORT).show( );
                d.dismiss();
            }}
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Toast.makeText(ListeProduits.this, "annuler", Toast.LENGTH_SHORT).show( );

                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();


        }/*/


    public void showDialogerror(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                dialog.dismiss( );
            }
        });

        dialog.show( );

    }


    public void showDialogconfirmed(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogvalid);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                dialog.dismiss( );
            }
        });

        dialog.show( );

    }

}


