package com.example.myapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListeUsers extends AppCompatActivity {


    DataBase mydb;
    ListView liste;
    ImageView AjoutUser, supprimerCompte, modifierCompte;


    ArrayList<Compte> listCompte;
    UserListAdapter adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_users);

        liste = findViewById(R.id.liste_accounts);
        AjoutUser = findViewById(R.id.adduser);
        listCompte = new ArrayList<>( );

        mydb = new DataBase(this);
        mydb.open( );


        AjoutUser.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListeUsers.this, AddUser.class);
                startActivity(intent);
            }
        });


        liste.setAdapter(new UserListAdapter(this, mydb.listeUser( )));

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener( ) {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Compte compte = mydb.listeUser( ).get(i);
                Intent intent = new Intent(ListeUsers.this, UpdateUser.class);
                intent.putExtra("compte", compte);
                startActivity(intent);
                supprimerCompte = (ImageView) findViewById(R.id.supprimeruser);
                modifierCompte = (ImageView) findViewById(R.id.modifieruser);


                supprimerCompte.setOnClickListener(new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ListeUsers.this, "cc", Toast.LENGTH_SHORT).show( );

                    }
                });


            }
        });















     /*/   Cursor data = mydb.ListUser( );

        if (data.getCount( ) == 0) {
            Toast.makeText(ListeUsers.this, "Il n'existe aucun compte utilisateur", Toast.LENGTH_SHORT).show( );
        } else {
            while (data.moveToNext( )) {

                String nom = data.getString(1);
                String prenom = data.getString(2);
                //String login = data.getString(4);

                listCompte.add(new Compte(nom, prenom));
            }

            adapter = new UserListAdapter(this, R.layout.item_liste_accounts, listCompte);
            liste.setAdapter(adapter);


        }/*/

      /*/  supprimerCompte.setOnClickListener(new View.OnClickListener( ) {
             @Override
              public void onClick(View v) {

                 //listCompte.remove(0);
                // adapter.notifyDataSetChanged();


                 Toast tt = Toast.makeText(getApplicationContext( ), "supp ok ! ", Toast.LENGTH_LONG);
                 tt.show( );

             }
}


);}/*/
    }
}



    /*/ public class CustomListAdapter extends ArrayAdapter<String> {
        private DataBase mydb;
        private Activity context;

        List<String> listdata1;
        List<String> listdata2;
        List<String> listdata3;
        List<String> listdata4;
        List<String> listdata5;



        public CustomListAdapter(Activity context,  List<String> nom, List<String> prenom, List<String> role, List<String> login, List<String> mdp) {
            super(context, R.layout.item_liste_accounts);


            this.context = context;
            this.listdata1 = nom;
            this.listdata2 = prenom;
            this.listdata3 = role;
            this.listdata4 = login;
            this.listdata5 = mdp;


            public View getView(final int position, View view, ViewGroup parent){
                LayoutInflater inflater = context.getLayoutInflater( );
                View rowView = inflater.inflate(R.layout.item_liste_accounts, null, true);

                rowView.setBackgroundColor(position % 2 == 0 ? Color.parseColor("#f2f2f2") : Color.rgb(255, 255, 255));


                ImageView supprimer = findViewById(R.id.supprimeruser);


                //final String cmd = listdata1.get(position).toString( );

                supprimer.setOnClickListener(new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext( ));
                        builder1.setMessage("Etes-vous sure de supprimer " + listdata2.get(position).toString( ) + " " + listdata3.get(position).toString( ) + " ?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton("Oui", new DialogInterface.OnClickListener( ) {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel( );


                                mydb = new DataBase(getContext( ));
                                mydb.open( );
                                mydb.delete_user_account();


                                listdata1.remove(position);
                                listdata2.remove(position);
                                listdata3.remove(position);
                                listdata4.remove(position);
                                listdata5.remove(position);

                                adapter.notifyDataSetChanged( );

                            }
                        });

                        builder1.setNegativeButton("Annuler", new DialogInterface.OnClickListener( ) {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel( );
                            }
                        });

                        AlertDialog alert11 = builder1.create( );
                        alert11.show( );


                    }
                });


            }


        }
    }/*/
