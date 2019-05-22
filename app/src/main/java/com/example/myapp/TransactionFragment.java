package com.example.myapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.DataBase;
import com.example.myapp.R;
import com.example.myapp.TRANSACTION;
import com.nexgo.oaf.apiv3.APIProxy;
import com.nexgo.oaf.apiv3.DeviceEngine;
import com.nexgo.oaf.apiv3.device.printer.AlignEnum;
import com.nexgo.oaf.apiv3.device.printer.OnPrintListener;
import com.nexgo.oaf.apiv3.device.printer.Printer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TransactionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionFragment extends Fragment {
    String datetime;
    String datetimeTicket;
    TabHost host;
    private DataBase mydb;
    Calendar myCalendar;
    EditText edittext;
    EditText edittext2;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionFragment newInstance(String param1, String param2) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.fragment_transaction, container, false);

        final DeviceEngine deviceEngine = APIProxy.getDeviceEngine() ;
        host = (TabHost) v.findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Journal");
        spec.setContent(R.id.tab1);

        spec.setIndicator("Journal",  ResourcesCompat.getDrawable(getResources(), R.drawable.ic_right, null));

        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Periodique");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Periodique",  ResourcesCompat.getDrawable(getResources(), R.drawable.ic_right, null));

        host.addTab(spec);



        host.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#ffffff")); // unselected
        TextView tv = (TextView) host.getTabWidget().getChildAt(0).findViewById(android.R.id.title);

        //Unselected Tabs
        tv.setTextColor(Color.parseColor("#00a6cf"));
        host.setBackgroundResource(R.color.colorBlocEffect);




        host.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#00a6cf")); // unselected
        TextView tv2 = (TextView) host.getTabWidget().getChildAt(1).findViewById(android.R.id.title);

        //Unselected Tabs
        tv2.setTextColor(Color.parseColor("#ffffff"));
        host.setBackgroundResource(R.color.colorBlocEffect);




        host.getTabWidget().getChildAt(host.getCurrentTab()).setBackgroundColor((getContext().getResources().getColor(R.color.colorWhite))); // selected
        TextView tv3 = (TextView) host.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv3.setTextColor(Color.parseColor("#00a6cf"));


        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String arg0) {

                int a = host.getCurrentTab();

                if(a==1){


                    host.getTabWidget().getChildAt(host.getCurrentTab()).setBackgroundColor(Color.parseColor("#ffffff")); // selected
                    TextView tva = (TextView) host.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                    tva.setTextColor(Color.parseColor("#00a6cf"));



                    host.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#00a6cf")); // selected
                    TextView tv2 = (TextView) host.getTabWidget().getChildAt(0).findViewById(android.R.id.title); //for Selected Tab
                    tv2.setTextColor(Color.parseColor("#ffffff"));

                }else {


                    host.getTabWidget().getChildAt(host.getCurrentTab()).setBackgroundColor(Color.parseColor("#ffffff")); // selected
                    TextView tvb = (TextView) host.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                    tvb.setTextColor(Color.parseColor("#00a6cf"));



                    host.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#00a6cf")); // selected
                    TextView tv2 = (TextView) host.getTabWidget().getChildAt(1).findViewById(android.R.id.title); //for Selected Tab
                    tv2.setTextColor(Color.parseColor("#ffffff"));


                }
            }

        });

        final TextView nbretrsAchat =(TextView) v.findViewById(R.id.textView14);
        final TextView sommetrsAchat =(TextView) v.findViewById(R.id.textView42);
        final TextView sommeptscadeaux =(TextView) v.findViewById(R.id.textView48);
        final TextView nbretrscadeaux =(TextView) v.findViewById(R.id.textView49);


        final TextView nbretrsAchat2 =(TextView) v.findViewById(R.id.textView50);
        final TextView sommetrsAchat2 =(TextView) v.findViewById(R.id.textView51);
        final TextView sommeptscadeaux2 =(TextView) v.findViewById(R.id.textView53);
        final TextView nbretrscadeaux2 =(TextView) v.findViewById(R.id.textView52);

        Button btnimprimer = (Button) v.findViewById(R.id.buttonimprimer);
        Button btnimprimerjornal = (Button) v.findViewById(R.id.imprimerjournal);



        Button btnshowsearch = (Button) v.findViewById(R.id.button9);
        final FrameLayout FL = (FrameLayout) v.findViewById(R.id.FL_search);
        FL.setVisibility(View.GONE);

        final FrameLayout FL2 = (FrameLayout) v.findViewById(R.id.frameLayout2);
        FL2.setVisibility(View.VISIBLE);



        View vs = getActivity().getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(vs.getWindowToken(),0);

        mydb=new DataBase(getContext());
        mydb.open();
        final String id_terminal = mydb.Select_IDTerminal();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        datetime = dateformat.format(c.getTime());

        SimpleDateFormat dateformat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        datetimeTicket=dateformat2.format(c.getTime());
        final int[] irecherche = {0};





        btnimprimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mydb=new DataBase(getContext());
                mydb.open();




                List<TRANSACTION> ListeTransactionAchat = new ArrayList<TRANSACTION>();
                List<TRANSACTION> ListeTransactionCadeaux = new ArrayList<TRANSACTION>();



                if(irecherche[0] == 1){

                    int sumAchat = mydb.GetAllTransactionsSumByPeriodeAndType(edittext.getText().toString(), edittext2.getText().toString(),"Q");
                    int nbrtrsAchat = mydb.GetAllTransactionsCountByPeriodAndType(edittext.getText().toString(), edittext2.getText().toString(),"Q");

                 //  int sumcad = mydb.GetAllTransactionsSumByPeriodeAndType(edittext.getText().toString(), edittext2.getText().toString(),"x");
                //   int nbrtrscad = mydb.GetAllTransactionsCountByPeriodAndType(edittext.getText().toString(), edittext2.getText().toString(),"x");

                    int somme = mydb.GetAllTransactionsSumByPeriode(edittext.getText().toString(), edittext2.getText().toString());
                    int f = mydb.GetAllTransactionsCountByPeriod(edittext.getText().toString(), edittext2.getText().toString());







                    Printer printer = deviceEngine.getPrinter();
                    printer.initPrinter();
                    printer.setTypeface(Typeface.SANS_SERIF);

                    //Print Logo

                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logoipablack);
                    printer.appendImage(bmp, AlignEnum.CENTER);

                    printer.appendPrnStr("Le : " + datetimeTicket,28,AlignEnum.LEFT,false);
                    printer.appendPrnStr("ID Terminal: " + id_terminal,28,AlignEnum.LEFT,false);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);
                    printer.appendPrnStr("JOURNAL",32,AlignEnum.CENTER,true);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);
                    printer.appendPrnStr("Date Début : " + edittext.getText().toString(),28,AlignEnum.LEFT,false);
                    printer.appendPrnStr("Date Fin : " + edittext2.getText().toString(),28,AlignEnum.LEFT,false);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);


                    printer.appendPrnStr("ACHAT",28,AlignEnum.CENTER,true);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);




                    if (nbrtrsAchat!=0){
                        // printer.appendPrnStr("Transactions Achat : ",28,AlignEnum.LEFT,false);
                        final String[] data1 =  new String[nbrtrsAchat];
                        final String[] data2 =  new String[nbrtrsAchat];
                        final String[] data3 =  new String[nbrtrsAchat];
                        final String[] data4 =  new String[nbrtrsAchat];
                        final String[] data5 =  new String[nbrtrsAchat];
                        final String[] data6 =  new String[nbrtrsAchat];
                        final String[] data7 =  new String[nbrtrsAchat];
                        final String[] data8 =  new String[nbrtrsAchat];
                        final String[] data9 =  new String[nbrtrsAchat];
                        final String[] data10 =  new String[nbrtrsAchat];
                        final String[] data11 =  new String[nbrtrsAchat];
                        final String[] data12 =  new String[nbrtrsAchat];
                        final String[] data13 =  new String[nbrtrsAchat];
                        final String[] data14 =  new String[nbrtrsAchat];
                        final String[] data15 =  new String[nbrtrsAchat];
                        final String[] data16 =  new String[nbrtrsAchat];



                        ListeTransactionAchat =  mydb.GetAllTransactionsByPeriodeAndType(edittext.getText().toString(), edittext2.getText().toString(),"Q");
                        int r;

                        for (r=0;r<nbrtrsAchat;r++){
                            data1[r] = ListeTransactionAchat.get(r).getId_transactions();
                            data2[r] = ListeTransactionAchat.get(r).getType_transaction();
                            data3[r] = ListeTransactionAchat.get(r).getNum_ticket();
                            data4[r] = ListeTransactionAchat.get(r).getNum_carte();
                            data5[r] = ListeTransactionAchat.get(r).getDate();
                            data6[r] = ListeTransactionAchat.get(r).getHeure();
                            data7[r] = ListeTransactionAchat.get(r).getNbre_point_global_install();
                            data8[r] = ListeTransactionAchat.get(r).getNbre_point_global_affilie();
                            data9[r] = ListeTransactionAchat.get(r).getEtat_transaction();
                            data10[r] = ListeTransactionAchat.get(r).getTicket_Annul_Trans();
                            data11[r] = ListeTransactionAchat.get(r).getTransac_nbr_passage();
                            data12[r] = ListeTransactionAchat.get(r).getTransac_nbr();
                            data13[r] = ListeTransactionAchat.get(r).getNumero_autorisation();
                            data14[r] = ListeTransactionAchat.get(r).getQuantite_glob();
                            data15[r] = ListeTransactionAchat.get(r).getId_request();
                            data16[r] = ListeTransactionAchat.get(r).getDetail_Transaction();

                        }

                        for (r=0;r<nbrtrsAchat;r++) {
                            printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);

                            String type = new String();



                            printer.appendPrnStr(ListeTransactionAchat.get(r).getNum_ticket() , 28, AlignEnum.LEFT, false);
                            printer.appendPrnStr(ListeTransactionAchat.get(r).getDate() + "  " + ListeTransactionAchat.get(r).getHeure(), 28, AlignEnum.LEFT, false);

                            printer.appendPrnStr(ListeTransactionAchat.get(r).getNum_carte() + "  " + ListeTransactionAchat.get(r).getNbre_point_global_install() +  " Pts " , 28, AlignEnum.LEFT, false);

                            printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);



                        }

                    }else{

                        printer.appendPrnStr("Pas de transaction Achat ",28,AlignEnum.CENTER,false);
                        printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);

                    }


                    printer.appendPrnStr("Total PTS Achat: " + String.valueOf(sumAchat) +" PTS",28,AlignEnum.LEFT,false);
                    printer.appendPrnStr("Nbre Transac Achat: " + String.valueOf(nbrtrsAchat) ,28,AlignEnum.LEFT,false);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);





                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);
                    printer.appendPrnStr("CADEAUX",28,AlignEnum.CENTER,true);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);



                    if (nbrtrscad!=0){
                        // printer.appendPrnStr("Transactions cadeaux : ",28,AlignEnum.LEFT,false);


                        final String[] data1 =  new String[nbrtrscad];
                        final String[] data2 =  new String[nbrtrscad];
                        final String[] data3 =  new String[nbrtrscad];
                        final String[] data4 =  new String[nbrtrscad];
                        final String[] data5 =  new String[nbrtrscad];
                        final String[] data6 =  new String[nbrtrscad];
                        final String[] data7 =  new String[nbrtrscad];
                        final String[] data8 =  new String[nbrtrscad];
                        final String[] data9 =  new String[nbrtrscad];
                        final String[] data10 =  new String[nbrtrscad];
                        final String[] data11 =  new String[nbrtrscad];
                        final String[] data12 =  new String[nbrtrscad];
                        final String[] data13 =  new String[nbrtrscad];
                        final String[] data14 =  new String[nbrtrscad];
                        final String[] data15 =  new String[nbrtrscad];
                        final String[] data16 =  new String[nbrtrscad];



                        ListeTransactionCadeaux =  mydb.GetAllTransactionsByPeriodeAndType(edittext.getText().toString(), edittext2.getText().toString(),"x");
                        int r;

                        for (r=0;r<nbrtrscad;r++){
                            data1[r] = ListeTransactionCadeaux.get(r).getId_transactions();
                            data2[r] = ListeTransactionCadeaux.get(r).getType_transaction();
                            data3[r] = ListeTransactionCadeaux.get(r).getNum_ticket();
                            data4[r] = ListeTransactionCadeaux.get(r).getNum_carte();
                            data5[r] = ListeTransactionCadeaux.get(r).getDate();
                            data6[r] = ListeTransactionCadeaux.get(r).getHeure();
                            data7[r] = ListeTransactionCadeaux.get(r).getNbre_point_global_install();
                            data8[r] = ListeTransactionCadeaux.get(r).getNbre_point_global_affilie();
                            data9[r] = ListeTransactionCadeaux.get(r).getEtat_transaction();
                            data10[r] = ListeTransactionCadeaux.get(r).getTicket_Annul_Trans();
                            data11[r] = ListeTransactionCadeaux.get(r).getTransac_nbr_passage();
                            data12[r] = ListeTransactionCadeaux.get(r).getTransac_nbr();
                            data13[r] = ListeTransactionCadeaux.get(r).getNumero_autorisation();
                            data14[r] = ListeTransactionCadeaux.get(r).getQuantite_glob();
                            data15[r] = ListeTransactionCadeaux.get(r).getId_request();
                            data16[r] = ListeTransactionCadeaux.get(r).getDetail_Transaction();

                        }

                        for (r=0;r<nbrtrscad;r++) {
                            printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);




                            printer.appendPrnStr(ListeTransactionCadeaux.get(r).getNum_ticket(), 28, AlignEnum.LEFT, false);
                            printer.appendPrnStr( ListeTransactionCadeaux.get(r).getDate() + "  " + ListeTransactionCadeaux.get(r).getHeure(), 28, AlignEnum.LEFT, false);

                            printer.appendPrnStr(ListeTransactionCadeaux.get(r).getNum_carte() + "  " + ListeTransactionCadeaux.get(r).getNbre_point_global_install() +  " Pts " , 28, AlignEnum.LEFT, false);

                            printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);



                        }

                    }else{
                        printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);

                        printer.appendPrnStr("Pas de transaction Cadeaux: ",28,AlignEnum.CENTER,false);
                        printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);

                    }



                    printer.appendPrnStr("Total PTS Cadeaux: " + String.valueOf(sumcad) +" PTS",28,AlignEnum.LEFT,false);
                    printer.appendPrnStr("Nbre Transac Cadeaux: " + String.valueOf(nbrtrscad) ,28,AlignEnum.LEFT,false);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);



                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);




                    printer.feedPaper(5);
                    printer.cutPaper();


                    printer.startPrint(true, new OnPrintListener() {
                        @Override
                        public void onPrintResult(final int retCode) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    //    Toast.makeText(getContext(), retCode + " Impression", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });




                    printer.feedPaper(5);
                    printer.cutPaper();

                    printer.startPrint(true, new OnPrintListener() {
                        @Override
                        public void onPrintResult(final int retCode) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    //  Toast.makeText(getContext(), retCode + " Impression", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });



                }else{




                    int sumAchat = mydb.GetAllTransactionsSumByType("Q");
                    int nbrtrsAchat = mydb.GetAllTransactionsCountByType("Q");

                    int sumcad = mydb.GetAllTransactionsSumByType("x");
                    int nbrtrscad = mydb.GetAllTransactionsCountByType("x");

                    int somme = mydb.GetAllTransactionsSum();
                    int f = mydb.GetAllTransactionsCount();


                    Printer printer = deviceEngine.getPrinter();
                    printer.initPrinter();
                    printer.setTypeface(Typeface.SANS_SERIF);

                    //Print Logo

                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logoipablack);
                    printer.appendImage(bmp, AlignEnum.CENTER);

                    printer.appendPrnStr("Le : " + datetimeTicket,28,AlignEnum.LEFT,false);
                    printer.appendPrnStr("ID Terminal: " + id_terminal,28,AlignEnum.LEFT,false);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);
                    printer.appendPrnStr("JOURNAL",32,AlignEnum.CENTER,true);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);
                    printer.appendPrnStr("TOUS LES TRANSACTIONS",28,AlignEnum.CENTER,false);


                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);
                    printer.appendPrnStr("ACHAT",28,AlignEnum.CENTER,true);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);




                    if (nbrtrsAchat!=0){
                        printer.appendPrnStr("Transactions Achat : ",28,AlignEnum.LEFT,false);
                        final String[] data1 =  new String[nbrtrsAchat];
                        final String[] data2 =  new String[nbrtrsAchat];
                        final String[] data3 =  new String[nbrtrsAchat];
                        final String[] data4 =  new String[nbrtrsAchat];
                        final String[] data5 =  new String[nbrtrsAchat];
                        final String[] data6 =  new String[nbrtrsAchat];
                        final String[] data7 =  new String[nbrtrsAchat];
                        final String[] data8 =  new String[nbrtrsAchat];
                        final String[] data9 =  new String[nbrtrsAchat];
                        final String[] data10 =  new String[nbrtrsAchat];
                        final String[] data11 =  new String[nbrtrsAchat];
                        final String[] data12 =  new String[nbrtrsAchat];
                        final String[] data13 =  new String[nbrtrsAchat];
                        final String[] data14 =  new String[nbrtrsAchat];
                        final String[] data15 =  new String[nbrtrsAchat];
                        final String[] data16 =  new String[nbrtrsAchat];



                        ListeTransactionAchat =  mydb.GetAllTransactionsByType("Q");
                        int r;

                        for (r=0;r<nbrtrsAchat;r++){
                            data1[r] = ListeTransactionAchat.get(r).getId_transactions();
                            data2[r] = ListeTransactionAchat.get(r).getType_transaction();
                            data3[r] = ListeTransactionAchat.get(r).getNum_ticket();
                            data4[r] = ListeTransactionAchat.get(r).getNum_carte();
                            data5[r] = ListeTransactionAchat.get(r).getDate();
                            data6[r] = ListeTransactionAchat.get(r).getHeure();
                            data7[r] = ListeTransactionAchat.get(r).getNbre_point_global_install();
                            data8[r] = ListeTransactionAchat.get(r).getNbre_point_global_affilie();
                            data9[r] = ListeTransactionAchat.get(r).getEtat_transaction();
                            data10[r] = ListeTransactionAchat.get(r).getTicket_Annul_Trans();
                            data11[r] = ListeTransactionAchat.get(r).getTransac_nbr_passage();
                            data12[r] = ListeTransactionAchat.get(r).getTransac_nbr();
                            data13[r] = ListeTransactionAchat.get(r).getNumero_autorisation();
                            data14[r] = ListeTransactionAchat.get(r).getQuantite_glob();
                            data15[r] = ListeTransactionAchat.get(r).getId_request();
                            data16[r] = ListeTransactionAchat.get(r).getDetail_Transaction();

                        }

                        for (r=0;r<nbrtrsAchat;r++) {
                            printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);

                            String type = new String();



                            printer.appendPrnStr(ListeTransactionAchat.get(r).getNum_ticket() , 28, AlignEnum.LEFT, false);
                            printer.appendPrnStr(ListeTransactionAchat.get(r).getDate() + "  " + ListeTransactionAchat.get(r).getHeure(), 28, AlignEnum.LEFT, false);

                            printer.appendPrnStr(ListeTransactionAchat.get(r).getNum_carte() + "  " + ListeTransactionAchat.get(r).getNbre_point_global_install() +  " Pts " , 28, AlignEnum.LEFT, false);

                            printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);



                        }

                    }else{

                        printer.appendPrnStr("Pas de transaction Achat ",28,AlignEnum.CENTER,false);
                        printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);

                    }


                    printer.appendPrnStr("Total PTS Achat: " + String.valueOf(sumAchat) +" PTS",28,AlignEnum.LEFT,false);
                    printer.appendPrnStr("Nbre Transac Achat: " + String.valueOf(nbrtrsAchat) ,28,AlignEnum.LEFT,false);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);





                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);
                    printer.appendPrnStr("CADEAUX",28,AlignEnum.CENTER,true);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);



                    if (nbrtrscad!=0){
                        printer.appendPrnStr("Transactions cadeaux : ",28,AlignEnum.LEFT,false);


                        final String[] data1 =  new String[nbrtrscad];
                        final String[] data2 =  new String[nbrtrscad];
                        final String[] data3 =  new String[nbrtrscad];
                        final String[] data4 =  new String[nbrtrscad];
                        final String[] data5 =  new String[nbrtrscad];
                        final String[] data6 =  new String[nbrtrscad];
                        final String[] data7 =  new String[nbrtrscad];
                        final String[] data8 =  new String[nbrtrscad];
                        final String[] data9 =  new String[nbrtrscad];
                        final String[] data10 =  new String[nbrtrscad];
                        final String[] data11 =  new String[nbrtrscad];
                        final String[] data12 =  new String[nbrtrscad];
                        final String[] data13 =  new String[nbrtrscad];
                        final String[] data14 =  new String[nbrtrscad];
                        final String[] data15 =  new String[nbrtrscad];
                        final String[] data16 =  new String[nbrtrscad];



                        ListeTransactionCadeaux =  mydb.GetAllTransactionsByType("x");
                        int r;

                        for (r=0;r<nbrtrscad;r++){
                            data1[r] = ListeTransactionCadeaux.get(r).getId_transactions();
                            data2[r] = ListeTransactionCadeaux.get(r).getType_transaction();
                            data3[r] = ListeTransactionCadeaux.get(r).getNum_ticket();
                            data4[r] = ListeTransactionCadeaux.get(r).getNum_carte();
                            data5[r] = ListeTransactionCadeaux.get(r).getDate();
                            data6[r] = ListeTransactionCadeaux.get(r).getHeure();
                            data7[r] = ListeTransactionCadeaux.get(r).getNbre_point_global_install();
                            data8[r] = ListeTransactionCadeaux.get(r).getNbre_point_global_affilie();
                            data9[r] = ListeTransactionCadeaux.get(r).getEtat_transaction();
                            data10[r] = ListeTransactionCadeaux.get(r).getTicket_Annul_Trans();
                            data11[r] = ListeTransactionCadeaux.get(r).getTransac_nbr_passage();
                            data12[r] = ListeTransactionCadeaux.get(r).getTransac_nbr();
                            data13[r] = ListeTransactionCadeaux.get(r).getNumero_autorisation();
                            data14[r] = ListeTransactionCadeaux.get(r).getQuantite_glob();
                            data15[r] = ListeTransactionCadeaux.get(r).getId_request();
                            data16[r] = ListeTransactionCadeaux.get(r).getDetail_Transaction();

                        }

                        for (r=0;r<nbrtrscad;r++) {
                            printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);




                            printer.appendPrnStr(ListeTransactionCadeaux.get(r).getNum_ticket(), 28, AlignEnum.LEFT, false);
                            printer.appendPrnStr( ListeTransactionCadeaux.get(r).getDate() + "  " + ListeTransactionCadeaux.get(r).getHeure(), 28, AlignEnum.LEFT, false);

                            printer.appendPrnStr(ListeTransactionCadeaux.get(r).getNum_carte() + "  " + ListeTransactionCadeaux.get(r).getNbre_point_global_install() +  " Pts " , 28, AlignEnum.LEFT, false);

                            printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);



                        }

                    }else{
                        printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);

                        printer.appendPrnStr("Pas de transaction Cadeaux: ",28,AlignEnum.CENTER,false);
                        printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);

                    }



                    printer.appendPrnStr("Total PTS Cadeaux: " + String.valueOf(sumcad) +" PTS",28,AlignEnum.LEFT,false);
                    printer.appendPrnStr("Nbre Transac Cadeaux: " + String.valueOf(nbrtrscad) ,28,AlignEnum.LEFT,false);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);



                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);




                    printer.feedPaper(5);
                    printer.cutPaper();


                    printer.startPrint(true, new OnPrintListener() {
                        @Override
                        public void onPrintResult(final int retCode) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    //    Toast.makeText(getContext(), retCode + " Impression", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });




                    printer.feedPaper(5);
                    printer.cutPaper();

                    printer.startPrint(true, new OnPrintListener() {
                        @Override
                        public void onPrintResult(final int retCode) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    //  Toast.makeText(getContext(), retCode + " Impression", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });




                }
            }
        });





















        btnimprimerjornal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                mydb=new DbAdapter(getContext());
                mydb.open();




                List<TRANSACTION> ListeTransactionAchat = new ArrayList<TRANSACTION>();
                List<TRANSACTION> ListeTransactionCadeaux = new ArrayList<TRANSACTION>();

                int nbrtrsAchat = mydb.GetAllTransactionsCountBydateAndType(datetime,"Q");
                int sumAchat = mydb.GetAllTransactionsSumByDateAndType(datetime,"Q");
                int nbrtrscad = mydb.GetAllTransactionsCountBydateAndType(datetime,"x");
                int sumcad = mydb.GetAllTransactionsSumByDateAndType(datetime,"x");

                int somme = mydb.GetAllTransactionsSumByDate(datetime);
                int f = mydb.GetAllTransactionsCountBydate(datetime);












                Printer printer = deviceEngine.getPrinter();
                printer.initPrinter();
                printer.setTypeface(Typeface.SANS_SERIF);

                //Print Logo

                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logoipablack);
                printer.appendImage(bmp, AlignEnum.CENTER);

                printer.appendPrnStr("Le : " + datetimeTicket,28,AlignEnum.LEFT,false);
                printer.appendPrnStr("ID Terminal: " + id_terminal,28,AlignEnum.LEFT,false);
                printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);
                printer.appendPrnStr("JOURNAL",32,AlignEnum.CENTER,true);
                printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);

                printer.appendPrnStr("ACHAT",28,AlignEnum.CENTER,true);
                printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);




                if (nbrtrsAchat!=0){
                    // printer.appendPrnStr("Transactions Achat : ",28,AlignEnum.LEFT,false);
                    final String[] data1 =  new String[nbrtrsAchat];
                    final String[] data2 =  new String[nbrtrsAchat];
                    final String[] data3 =  new String[nbrtrsAchat];
                    final String[] data4 =  new String[nbrtrsAchat];
                    final String[] data5 =  new String[nbrtrsAchat];
                    final String[] data6 =  new String[nbrtrsAchat];
                    final String[] data7 =  new String[nbrtrsAchat];
                    final String[] data8 =  new String[nbrtrsAchat];
                    final String[] data9 =  new String[nbrtrsAchat];
                    final String[] data10 =  new String[nbrtrsAchat];
                    final String[] data11 =  new String[nbrtrsAchat];
                    final String[] data12 =  new String[nbrtrsAchat];
                    final String[] data13 =  new String[nbrtrsAchat];
                    final String[] data14 =  new String[nbrtrsAchat];
                    final String[] data15 =  new String[nbrtrsAchat];
                    final String[] data16 =  new String[nbrtrsAchat];



                    ListeTransactionAchat =  mydb.GetAllTransactionsByDateAndType(datetime,"Q");
                    int r;

                    for (r=0;r<nbrtrsAchat;r++){
                        data1[r] = ListeTransactionAchat.get(r).getId_transactions();
                        data2[r] = ListeTransactionAchat.get(r).getType_transaction();
                        data3[r] = ListeTransactionAchat.get(r).getNum_ticket();
                        data4[r] = ListeTransactionAchat.get(r).getNum_carte();
                        data5[r] = ListeTransactionAchat.get(r).getDate();
                        data6[r] = ListeTransactionAchat.get(r).getHeure();
                        data7[r] = ListeTransactionAchat.get(r).getNbre_point_global_install();
                        data8[r] = ListeTransactionAchat.get(r).getNbre_point_global_affilie();
                        data9[r] = ListeTransactionAchat.get(r).getEtat_transaction();
                        data10[r] = ListeTransactionAchat.get(r).getTicket_Annul_Trans();
                        data11[r] = ListeTransactionAchat.get(r).getTransac_nbr_passage();
                        data12[r] = ListeTransactionAchat.get(r).getTransac_nbr();
                        data13[r] = ListeTransactionAchat.get(r).getNumero_autorisation();
                        data14[r] = ListeTransactionAchat.get(r).getQuantite_glob();
                        data15[r] = ListeTransactionAchat.get(r).getId_request();
                        data16[r] = ListeTransactionAchat.get(r).getDetail_Transaction();

                    }

                    for (r=0;r<nbrtrsAchat;r++) {
                        printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);

                        String type = new String();



                        printer.appendPrnStr(ListeTransactionAchat.get(r).getNum_ticket() , 28, AlignEnum.LEFT, false);
                        printer.appendPrnStr(ListeTransactionAchat.get(r).getDate() + "  " + ListeTransactionAchat.get(r).getHeure(), 28, AlignEnum.LEFT, false);

                        printer.appendPrnStr(ListeTransactionAchat.get(r).getNum_carte() + "  " + ListeTransactionAchat.get(r).getNbre_point_global_install() +  " Pts " , 28, AlignEnum.LEFT, false);

                        printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);



                    }

                }else{

                    printer.appendPrnStr("Pas de transaction Achat ",28,AlignEnum.CENTER,false);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);

                }


                printer.appendPrnStr("Total PTS Achat: " + String.valueOf(sumAchat) +" PTS",28,AlignEnum.LEFT,false);
                printer.appendPrnStr("Nbre Transac Achat: " + String.valueOf(nbrtrsAchat) ,28,AlignEnum.LEFT,false);
                printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);





                printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);
                printer.appendPrnStr("CADEAUX",28,AlignEnum.CENTER,true);
                printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);



                if (nbrtrscad!=0){
                    //  printer.appendPrnStr("Transactions cadeaux : ",28,AlignEnum.LEFT,false);


                    final String[] data1 =  new String[nbrtrscad];
                    final String[] data2 =  new String[nbrtrscad];
                    final String[] data3 =  new String[nbrtrscad];
                    final String[] data4 =  new String[nbrtrscad];
                    final String[] data5 =  new String[nbrtrscad];
                    final String[] data6 =  new String[nbrtrscad];
                    final String[] data7 =  new String[nbrtrscad];
                    final String[] data8 =  new String[nbrtrscad];
                    final String[] data9 =  new String[nbrtrscad];
                    final String[] data10 =  new String[nbrtrscad];
                    final String[] data11 =  new String[nbrtrscad];
                    final String[] data12 =  new String[nbrtrscad];
                    final String[] data13 =  new String[nbrtrscad];
                    final String[] data14 =  new String[nbrtrscad];
                    final String[] data15 =  new String[nbrtrscad];
                    final String[] data16 =  new String[nbrtrscad];



                    ListeTransactionCadeaux =  mydb.GetAllTransactionsByDateAndType(datetime,"x");
                    int r;

                    for (r=0;r<nbrtrscad;r++){
                        data1[r] = ListeTransactionCadeaux.get(r).getId_transactions();
                        data2[r] = ListeTransactionCadeaux.get(r).getType_transaction();
                        data3[r] = ListeTransactionCadeaux.get(r).getNum_ticket();
                        data4[r] = ListeTransactionCadeaux.get(r).getNum_carte();
                        data5[r] = ListeTransactionCadeaux.get(r).getDate();
                        data6[r] = ListeTransactionCadeaux.get(r).getHeure();
                        data7[r] = ListeTransactionCadeaux.get(r).getNbre_point_global_install();
                        data8[r] = ListeTransactionCadeaux.get(r).getNbre_point_global_affilie();
                        data9[r] = ListeTransactionCadeaux.get(r).getEtat_transaction();
                        data10[r] = ListeTransactionCadeaux.get(r).getTicket_Annul_Trans();
                        data11[r] = ListeTransactionCadeaux.get(r).getTransac_nbr_passage();
                        data12[r] = ListeTransactionCadeaux.get(r).getTransac_nbr();
                        data13[r] = ListeTransactionCadeaux.get(r).getNumero_autorisation();
                        data14[r] = ListeTransactionCadeaux.get(r).getQuantite_glob();
                        data15[r] = ListeTransactionCadeaux.get(r).getId_request();
                        data16[r] = ListeTransactionCadeaux.get(r).getDetail_Transaction();

                    }

                    for (r=0;r<nbrtrscad;r++) {
                        printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);




                        printer.appendPrnStr(ListeTransactionCadeaux.get(r).getNum_ticket(), 28, AlignEnum.LEFT, false);
                        printer.appendPrnStr( ListeTransactionCadeaux.get(r).getDate() + "  " + ListeTransactionCadeaux.get(r).getHeure(), 28, AlignEnum.LEFT, false);

                        printer.appendPrnStr(ListeTransactionCadeaux.get(r).getNum_carte() + "  " + ListeTransactionCadeaux.get(r).getNbre_point_global_install() +  " Pts " , 28, AlignEnum.LEFT, false);

                        printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);



                    }

                }else{
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);

                    printer.appendPrnStr("Pas de transaction Cadeaux: ",28,AlignEnum.CENTER,false);
                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);

                }



                printer.appendPrnStr("Total PTS Cadeaux: " + String.valueOf(sumcad) +" PTS",28,AlignEnum.LEFT,false);
                printer.appendPrnStr("Nbre Transac Cadeaux: " + String.valueOf(nbrtrscad) ,28,AlignEnum.LEFT,false);
                printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);




































                printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);
                printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);




                printer.feedPaper(5);
                printer.cutPaper();


                printer.startPrint(true, new OnPrintListener() {
                    @Override
                    public void onPrintResult(final int retCode) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //    Toast.makeText(getContext(), retCode + " Impression", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });


                              /*  int r;
                                for (r=0;r<nbr3;r++){
                                    // printer.appendPrnStr("" + data1[r] +"        " +  data4[r],24,AlignEnum.LEFT,true);

                                    printer.appendPrnStr(data1[r] ,data4[r] ,24,true);
                                    printer.appendPrnStr(data3[r] +"        " +  data5[r] ,data4[0] ,24,false);

                                    //  printer.appendPrnStr("" + data3[r] +"        " +  data5[r] +"       "+data2[r],24,AlignEnum.LEFT,false);
                                    printer.appendPrnStr("                                             ",24,AlignEnum.LEFT,false);

                                }*/

                printer.feedPaper(5);
                printer.cutPaper();


                printer.startPrint(true, new OnPrintListener() {
                    @Override
                    public void onPrintResult(final int retCode) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //  Toast.makeText(getContext(), retCode + " Impression", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });



            }
        });





























        btnshowsearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FL2.setVisibility(View.GONE);
                FL.setVisibility(View.VISIBLE);
                FL.setAlpha(0.0f);

                FL.animate()
                        .translationY(0)
                        .setDuration(1000)
                        .alpha(1.0f);


            }
        });


        Button imgBt = (Button) v.findViewById(R.id.bt_hide);
        imgBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FL.setAlpha(1.0f);
                FL.animate()
                        .translationY(-180)
                        .setDuration(1000)
                        .alpha(0.0f);
                FL.setVisibility(View.GONE);
                FL2.setVisibility(View.VISIBLE);

            }
        });

        final ListView lv2 = (ListView) v.findViewById(R.id.lv2);
        final ListView lv3 = (ListView) v.findViewById(R.id.lv3);
        Button btnrecherche = (Button) v.findViewById(R.id.recherche);
        Date currentTime = Calendar.getInstance().getTime();








        mydb=new DbAdapter(getContext());
        mydb.open();

        List<TRANSACTION> ListeTransaction2 = new ArrayList<TRANSACTION>();

        int sommeAchat = mydb.GetAllTransactionsSumByType("Q");
        int fAchat = mydb.GetAllTransactionsCountByType("Q");

        int sommecad = mydb.GetAllTransactionsSumByType("x");
        int fcad = mydb.GetAllTransactionsCountByType("x");




        int somme2 = mydb.GetAllTransactionsSum();
        int f2 = mydb.GetAllTransactionsCount();


        nbretrsAchat2.setText("Nbre Des Transaction(s) Achat: "+ String.valueOf(fAchat));
        sommetrsAchat2.setText("Total Point(s) Achat: "+String.valueOf(sommeAchat) +" PTS");


        nbretrscadeaux2.setText("Nbre Des Transaction(s) Cadeaux: "+ String.valueOf(fcad));
        sommeptscadeaux2.setText("Total Point(s) Cadeaux: "+String.valueOf(sommecad) +" PTS");


        if (f2!=0){

            final String[] data1 =  new String[f2];
            final String[] data2 =  new String[f2];
            final String[] data3 =  new String[f2];
            final String[] data4 =  new String[f2];
            final String[] data5 =  new String[f2];
            final String[] data6 =  new String[f2];
            final String[] data7 =  new String[f2];
            final String[] data8 =  new String[f2];
            final String[] data9 =  new String[f2];
            final String[] data10 =  new String[f2];
            final String[] data11 =  new String[f2];
            final String[] data12 =  new String[f2];
            final String[] data13 =  new String[f2];
            final String[] data14 =  new String[f2];
            final String[] data15 =  new String[f2];
            final String[] data16 =  new String[f2];



            ListeTransaction2 =  mydb.GetAllTransactions();
            int r;

            for (r=0;r<f2;r++){
                data1[r] = ListeTransaction2.get(r).getId_transactions();
                data2[r] = ListeTransaction2.get(r).getType_transaction();
                data3[r] = ListeTransaction2.get(r).getNum_ticket();
                data4[r] = ListeTransaction2.get(r).getNum_carte();
                data5[r] = ListeTransaction2.get(r).getDate();
                data6[r] = ListeTransaction2.get(r).getHeure();
                data7[r] = ListeTransaction2.get(r).getNbre_point_global_install();
                data8[r] = ListeTransaction2.get(r).getNbre_point_global_affilie();
                data9[r] = ListeTransaction2.get(r).getEtat_transaction();
                data10[r] = ListeTransaction2.get(r).getTicket_Annul_Trans();
                data11[r] = ListeTransaction2.get(r).getTransac_nbr_passage();
                data12[r] = ListeTransaction2.get(r).getTransac_nbr();
                data13[r] = ListeTransaction2.get(r).getNumero_autorisation();
                data14[r] = ListeTransaction2.get(r).getQuantite_glob();
                data15[r] = ListeTransaction2.get(r).getId_request();
                data16[r] = ListeTransaction2.get(r).getDetail_Transaction();

            }

            List<String> listdata1;
            List<String> listdata2;
            List<String> listdata3;
            List<String> listdata4;
            List<String> listdata5;
            List<String> listdata6;
            List<String> listdata7;
            List<String> listdata8;
            List<String> listdata9;
            List<String> listdata10;
            List<String> listdata11;
            List<String> listdata12;
            List<String> listdata13;
            List<String> listdata14;
            List<String> listdata15;
            List<String> listdata16;

            listdata1 = new ArrayList<String>(Arrays.asList(data1));
            listdata2 = new ArrayList<String>(Arrays.asList(data2));
            listdata3 = new ArrayList<String>(Arrays.asList(data3));
            listdata4 = new ArrayList<String>(Arrays.asList(data4));
            listdata5 = new ArrayList<String>(Arrays.asList(data5));
            listdata6 = new ArrayList<String>(Arrays.asList(data6));
            listdata7 = new ArrayList<String>(Arrays.asList(data7));
            listdata8 = new ArrayList<String>(Arrays.asList(data8));
            listdata9 = new ArrayList<String>(Arrays.asList(data9));
            listdata10 = new ArrayList<String>(Arrays.asList(data10));
            listdata11 = new ArrayList<String>(Arrays.asList(data11));
            listdata12 = new ArrayList<String>(Arrays.asList(data12));
            listdata13 = new ArrayList<String>(Arrays.asList(data13));
            listdata14 = new ArrayList<String>(Arrays.asList(data14));
            listdata15 = new ArrayList<String>(Arrays.asList(data15));
            listdata16 = new ArrayList<String>(Arrays.asList(data16));

            CustomListTransactionAdapter adapter;
            adapter=new CustomListTransactionAdapter(getActivity(), listdata1,listdata2,listdata3,listdata4,listdata5,listdata6,listdata7, listdata8,listdata9,listdata10,listdata11,listdata12,listdata13,listdata14,listdata15,listdata16);

            lv3.setAdapter(adapter);






        }else{
            nbretrsAchat2.setText("Nbre des Transaction(s) Achat: 0");
            sommetrsAchat2.setText("Total Point(s) Achat: 0 PTS");

            nbretrscadeaux2.setText("Nbre Des Transaction(s) Cadeaux: 0");
            sommeptscadeaux2.setText("Total Point(s) Cadeaux: 0 PTS");


        }
























































        mydb=new DbAdapter(getContext());
        mydb.open();

        List<TRANSACTION> ListeTransaction = new ArrayList<TRANSACTION>();


        int nbrtrsAchat = mydb.GetAllTransactionsCountBydateAndType(datetime,"Q");
        int sumAchat = mydb.GetAllTransactionsSumByDateAndType(datetime,"Q");
        int nbrtrscad = mydb.GetAllTransactionsCountBydateAndType(datetime,"x");
        int sumcad = mydb.GetAllTransactionsSumByDateAndType(datetime,"x");

        int somme = mydb.GetAllTransactionsSumByDate(datetime);
        int f = mydb.GetAllTransactionsCountBydate(datetime);

        nbretrsAchat.setText("Nbre Des Transaction(s) Achat : "+ String.valueOf(nbrtrsAchat));
        sommetrsAchat.setText("Total Point(s) Achat: "+String.valueOf(sumAchat) +" PTS");

        nbretrscadeaux.setText("Nbre Des Transaction(s) Cadeaux : "+ String.valueOf(nbrtrscad));
        sommeptscadeaux.setText("Total Point(s) Cadeaux: "+String.valueOf(sumcad) +" PTS");

        if (f!=0){

            final String[] data1 =  new String[f];
            final String[] data2 =  new String[f];
            final String[] data3 =  new String[f];
            final String[] data4 =  new String[f];
            final String[] data5 =  new String[f];
            final String[] data6 =  new String[f];
            final String[] data7 =  new String[f];
            final String[] data8 =  new String[f];
            final String[] data9 =  new String[f];
            final String[] data10 =  new String[f];
            final String[] data11 =  new String[f];
            final String[] data12 =  new String[f];
            final String[] data13 =  new String[f];
            final String[] data14 =  new String[f];
            final String[] data15 =  new String[f];
            final String[] data16 =  new String[f];



            ListeTransaction =  mydb.GetAllTransactionsByDate(datetime);
            int r;

            for (r=0;r<f;r++){
                data1[r] = ListeTransaction.get(r).getId_transactions();
                data2[r] = ListeTransaction.get(r).getType_transaction();
                data3[r] = ListeTransaction.get(r).getNum_ticket();
                data4[r] = ListeTransaction.get(r).getNum_carte();
                data5[r] = ListeTransaction.get(r).getDate();
                data6[r] = ListeTransaction.get(r).getHeure();
                data7[r] = ListeTransaction.get(r).getNbre_point_global_install();
                data8[r] = ListeTransaction.get(r).getNbre_point_global_affilie();
                data9[r] = ListeTransaction.get(r).getEtat_transaction();
                data10[r] = ListeTransaction.get(r).getTicket_Annul_Trans();
                data11[r] = ListeTransaction.get(r).getTransac_nbr_passage();
                data12[r] = ListeTransaction.get(r).getTransac_nbr();
                data13[r] = ListeTransaction.get(r).getNumero_autorisation();
                data14[r] = ListeTransaction.get(r).getQuantite_glob();
                data15[r] = ListeTransaction.get(r).getId_request();
                data16[r] = ListeTransaction.get(r).getDetail_Transaction();

            }

            List<String> listdata1;
            List<String> listdata2;
            List<String> listdata3;
            List<String> listdata4;
            List<String> listdata5;
            List<String> listdata6;
            List<String> listdata7;
            List<String> listdata8;
            List<String> listdata9;
            List<String> listdata10;
            List<String> listdata11;
            List<String> listdata12;
            List<String> listdata13;
            List<String> listdata14;
            List<String> listdata15;
            List<String> listdata16;

            listdata1 = new ArrayList<String>(Arrays.asList(data1));
            listdata2 = new ArrayList<String>(Arrays.asList(data2));
            listdata3 = new ArrayList<String>(Arrays.asList(data3));
            listdata4 = new ArrayList<String>(Arrays.asList(data4));
            listdata5 = new ArrayList<String>(Arrays.asList(data5));
            listdata6 = new ArrayList<String>(Arrays.asList(data6));
            listdata7 = new ArrayList<String>(Arrays.asList(data7));
            listdata8 = new ArrayList<String>(Arrays.asList(data8));
            listdata9 = new ArrayList<String>(Arrays.asList(data9));
            listdata10 = new ArrayList<String>(Arrays.asList(data10));
            listdata11 = new ArrayList<String>(Arrays.asList(data11));
            listdata12 = new ArrayList<String>(Arrays.asList(data12));
            listdata13 = new ArrayList<String>(Arrays.asList(data13));
            listdata14 = new ArrayList<String>(Arrays.asList(data14));
            listdata15 = new ArrayList<String>(Arrays.asList(data15));
            listdata16 = new ArrayList<String>(Arrays.asList(data16));

            CustomListTransactionAdapter adapter;
            adapter=new CustomListTransactionAdapter(getActivity(), listdata1,listdata2,listdata3,listdata4,listdata5,listdata6,listdata7, listdata8,listdata9,listdata10,listdata11,listdata12,listdata13,listdata14,listdata15,listdata16);

            lv2.setAdapter(adapter);

/*
            lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    String Slecteditem= data1[+position];
                    Toast.makeText(getActivity(), Slecteditem, Toast.LENGTH_SHORT).show();

                }
            });*/




        }else{


            nbretrsAchat.setText("Nbre des transaction(s) Achat: 0");
            sommetrsAchat.setText("Total Point(s) Achat: 0 PTS");

            nbretrscadeaux.setText("Nbre des transaction(s) Cadeaux: 0");
            sommeptscadeaux.setText("Total Point(s) Cadeaux: 0 PTS");

        }

        final String[] selectedfield = {new String()};

        myCalendar = Calendar.getInstance();

        edittext= (EditText) v.findViewById(R.id.editText);

        edittext2 = (EditText) v.findViewById(R.id.editText2);

        edittext.setShowSoftInputOnFocus(false);
        edittext2.setShowSoftInputOnFocus(false);




        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                if (selectedfield[0].equals("1")){
                    edittext.setText(sdf.format(myCalendar.getTime()));

                }else{
                    edittext2.setText(sdf.format(myCalendar.getTime()));
                }



            }

        };

        edittext2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                edittext2.setShowSoftInputOnFocus(false);
                View s = getActivity().getCurrentFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(s.getWindowToken(),0);

                // inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                selectedfield[0] = "2";

            }
        });



        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                edittext.setShowSoftInputOnFocus(false);
                View s = getActivity().getCurrentFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(s.getWindowToken(),0);

                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                selectedfield[0] = "1";
            }
        });




        btnrecherche.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(edittext.getText().toString().equals("")){
                    edittext.setError("Ajouter le Date Début");

                }else if(edittext2.getText().toString().equals("")){

                    edittext2.setError("Ajouter le Date Fin");

                }else{
                    irecherche[0] = 1;
                    mydb=new DbAdapter(getContext());
                    mydb.open();

                    List<TRANSACTION> ListeTransaction3 = new ArrayList<TRANSACTION>();


                    int SommeAchatRecherche = mydb.GetAllTransactionsSumByPeriodeAndType(edittext.getText().toString(), edittext2.getText().toString(),"Q");
                    int nbreAchatRecherche = mydb.GetAllTransactionsCountByPeriodAndType(edittext.getText().toString(), edittext2.getText().toString(),"Q");
                    int SommeCadeauxRecherche = mydb.GetAllTransactionsSumByPeriodeAndType(edittext.getText().toString(), edittext2.getText().toString(),"x");
                    int nbreCadeauxRecherche = mydb.GetAllTransactionsCountByPeriodAndType(edittext.getText().toString(), edittext2.getText().toString(),"x");




                    int somme = mydb.GetAllTransactionsSumByPeriode(edittext.getText().toString(), edittext2.getText().toString());
                    int f3 = mydb.GetAllTransactionsCountByPeriod(edittext.getText().toString(), edittext2.getText().toString());

                    Log.e("trs fragment","f =" + String.valueOf(f3));
                    Log.e("trs fragment","tot pts =" + String.valueOf(somme));

                    nbretrsAchat2.setText("Nbre Des Transaction(s) Achat: "+ String.valueOf(nbreAchatRecherche));
                    sommetrsAchat2.setText("Total Point(s) Achat: "+String.valueOf(SommeAchatRecherche) +" PTS");
                    nbretrscadeaux2.setText("Nbre Des Transaction(s) Cadeaux: "+ String.valueOf(nbreCadeauxRecherche));
                    sommeptscadeaux2.setText("Total Point(s) Cadeaux: "+String.valueOf(SommeCadeauxRecherche) +" PTS");



                    if (f3!=0){
                        Log.e("trs fragment","f !=0");
                        final String[] data1 =  new String[f3];
                        final String[] data2 =  new String[f3];
                        final String[] data3 =  new String[f3];
                        final String[] data4 =  new String[f3];
                        final String[] data5 =  new String[f3];
                        final String[] data6 =  new String[f3];
                        final String[] data7 =  new String[f3];
                        final String[] data8 =  new String[f3];
                        final String[] data9 =  new String[f3];
                        final String[] data10 =  new String[f3];
                        final String[] data11 =  new String[f3];
                        final String[] data12 =  new String[f3];
                        final String[] data13 =  new String[f3];
                        final String[] data14 =  new String[f3];
                        final String[] data15 =  new String[f3];
                        final String[] data16 =  new String[f3];



                        ListeTransaction3 =  mydb.GetAllTransactionsByPeriode(edittext.getText().toString(), edittext2.getText().toString());
                        int r;

                        for (r=0;r<f3;r++){
                            String jj = ListeTransaction3.get(r).getId_transactions();
                            data1[r] = ListeTransaction3.get(r).getId_transactions();
                            data2[r] = ListeTransaction3.get(r).getType_transaction();
                            data3[r] = ListeTransaction3.get(r).getNum_ticket();
                            data4[r] = ListeTransaction3.get(r).getNum_carte();
                            data5[r] = ListeTransaction3.get(r).getDate();
                            data6[r] = ListeTransaction3.get(r).getHeure();
                            data7[r] = ListeTransaction3.get(r).getNbre_point_global_install();
                            data8[r] = ListeTransaction3.get(r).getNbre_point_global_affilie();
                            data9[r] = ListeTransaction3.get(r).getEtat_transaction();
                            data10[r] = ListeTransaction3.get(r).getTicket_Annul_Trans();
                            data11[r] = ListeTransaction3.get(r).getTransac_nbr_passage();
                            data12[r] = ListeTransaction3.get(r).getTransac_nbr();
                            data13[r] = ListeTransaction3.get(r).getNumero_autorisation();
                            data14[r] = ListeTransaction3.get(r).getQuantite_glob();
                            data15[r] = ListeTransaction3.get(r).getId_request();
                            data16[r] = ListeTransaction3.get(r).getDetail_Transaction();

                        }

                        List<String> listdata1;
                        List<String> listdata2;
                        List<String> listdata3;
                        List<String> listdata4;
                        List<String> listdata5;
                        List<String> listdata6;
                        List<String> listdata7;
                        List<String> listdata8;
                        List<String> listdata9;
                        List<String> listdata10;
                        List<String> listdata11;
                        List<String> listdata12;
                        List<String> listdata13;
                        List<String> listdata14;
                        List<String> listdata15;
                        List<String> listdata16;

                        listdata1 = new ArrayList<String>(Arrays.asList(data1));
                        listdata2 = new ArrayList<String>(Arrays.asList(data2));
                        listdata3 = new ArrayList<String>(Arrays.asList(data3));
                        listdata4 = new ArrayList<String>(Arrays.asList(data4));
                        listdata5 = new ArrayList<String>(Arrays.asList(data5));
                        listdata6 = new ArrayList<String>(Arrays.asList(data6));
                        listdata7 = new ArrayList<String>(Arrays.asList(data7));
                        listdata8 = new ArrayList<String>(Arrays.asList(data8));
                        listdata9 = new ArrayList<String>(Arrays.asList(data9));
                        listdata10 = new ArrayList<String>(Arrays.asList(data10));
                        listdata11 = new ArrayList<String>(Arrays.asList(data11));
                        listdata12 = new ArrayList<String>(Arrays.asList(data12));
                        listdata13 = new ArrayList<String>(Arrays.asList(data13));
                        listdata14 = new ArrayList<String>(Arrays.asList(data14));
                        listdata15 = new ArrayList<String>(Arrays.asList(data15));
                        listdata16 = new ArrayList<String>(Arrays.asList(data16));

                        CustomListTransactionAdapter adapter;
                        adapter=new CustomListTransactionAdapter(getActivity(), listdata1,listdata2,listdata3,listdata4,listdata5,listdata6,listdata7, listdata8,listdata9,listdata10,listdata11,listdata12,listdata13,listdata14,listdata15,listdata16);

                        lv3.setAdapter(adapter);
                        lv3.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();

                        FL.setVisibility(View.GONE);
                        FL2.setVisibility(View.VISIBLE);
                        lv3.setVisibility(View.VISIBLE);

                    }else{
                        FL.setVisibility(View.GONE);
                        FL2.setVisibility(View.VISIBLE);
                        lv3.setVisibility(View.INVISIBLE);

                        nbretrsAchat2.setText("Nbre des transaction(s) Achat: 0");
                        sommetrsAchat2.setText("Total Point(s) Achat: 0 PTS");

                        nbretrscadeaux2.setText("Nbre des transaction(s) Cadeaux: 0");
                        sommeptscadeaux2.setText("Total Point(s) Cadeaux: 0 PTS");
                    }


                }




            }
        });


        return v;
    }



    private void updateLabel() {

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }






    public class CustomListTransactionAdapter extends ArrayAdapter<String> {
        private DbAdapter mydb;
        private final Activity context;

        List<String> listdata1;
        List<String> listdata2;
        List<String> listdata3;
        List<String> listdata4;
        List<String> listdata5;
        List<String> listdata6;
        List<String> listdata7;
        List<String> listdata8;
        List<String> listdata9;
        List<String> listdata10;
        List<String> listdata11;
        List<String> listdata12;
        List<String> listdata13;
        List<String> listdata14;
        List<String> listdata15;
        List<String> listdata16;



        public CustomListTransactionAdapter(Activity context, List<String> Id_transactions, List<String> Type_transaction,
                                            List<String> Num_ticket, List<String> Num_carte, List<String> Date, List<String> Heure, List<String> Nbre_point_global_install
                , List<String> Nbre_point_global_affilie, List<String> Etat_transaction,List<String> Ticket_Annul_Trans, List<String> Transac_nbr_passage, List<String> Transac_nbr, List<String> Numero_autorisation, List<String> Quantite_glob
                , List<String> Id_request, List<String> Detail_Transaction) {
            super(context, R.layout.list_item, Id_transactions);
            // TODO Auto-generated constructor stub

            this.context=context;
            this.listdata1 = Id_transactions;
            this.listdata2 = Type_transaction;
            this.listdata3 = Num_ticket;
            this.listdata4 = Num_carte;
            this.listdata5 = Date;
            this.listdata6 = Heure;
            this.listdata7 = Nbre_point_global_install;
            this.listdata8 = Nbre_point_global_affilie;
            this.listdata9 = Etat_transaction;
            this.listdata10 = Ticket_Annul_Trans;
            this.listdata11 = Transac_nbr_passage;
            this.listdata12 = Transac_nbr;
            this.listdata13 = Numero_autorisation;
            this.listdata14 = Quantite_glob;
            this.listdata15 = Id_request;
            this.listdata16 = Detail_Transaction;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.list_item_transactions, null,true);

            rowView.setBackgroundColor(position % 2 == 0 ? Color.parseColor("#f2f2f2") : Color.rgb(255, 255, 255));

            ImageView supp = (ImageView) rowView.findViewById(R.id.imageView15);



            TextView txtTitle = (TextView) rowView.findViewById(R.id.item);

            if(listdata2.get(position).toString().equals("x")){

                Drawable img = getContext().getResources().getDrawable( R.drawable.ic_card_giftcard_blue_24dp );
                img.setBounds( 0, 0, 60, 60 );
                txtTitle.setCompoundDrawables( img, null, null, null );


            }

            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            //TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

            TextView txt21 = (TextView) rowView.findViewById(R.id.textView21);
            txt21.setText(listdata3.get(position).toString() + " " + listdata4.get(position).toString());

            TextView txt19 = (TextView) rowView.findViewById(R.id.textView19);
            txt19.setText(listdata5.get(position).toString() + " " +listdata6.get(position).toString());

            TextView txt43 = (TextView) rowView.findViewById(R.id.textView43);
            txt43.setText(listdata7.get(position).toString() + " PTS");

            return rowView;

        };
    }










}
