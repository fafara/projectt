package com.example.myapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ListeCategories extends AppCompatActivity {

    DataBase mydb;
    ListView liste;
    Button panier ;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_categories);

        liste = findViewById(R.id.liste_categorie);
        panier = findViewById(R.id.panier);

        mydb = new DataBase(getApplicationContext( ));
        mydb.open( );



        panier.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListeCategories.this, Panier.class);
                startActivity(intent);
            }
        });
        /*   ArrayAdapter<String> listeCategorieAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.item_liste_categorie
        );*/
        int layouList=    R.layout.item_liste_categorie;
        getIntent();
        int[] xml_id=new int[]{
        R.id.btnlistcat    };
        String[] colmn=new String[]{
                "nomcategorie"  };
        Cursor data = mydb.ListCat();

        if ( data.getCount() == 0){
            Toast.makeText(ListeCategories.this, "Base de données est vide", Toast.LENGTH_SHORT).show( );
        } else {

            /*while(data.moveToNext()){
                listeCategorieAdapter.add(data.getString(1));
                // ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listeAccountsAdapter);
                liste.setAdapter(listeCategorieAdapter);*/

            adapter=new SimpleCursorAdapter(this,layouList,data,colmn,xml_id,0);
            liste.setAdapter(adapter);
            }

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener( ) {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int position, long id) {
                Cursor pos= (Cursor) adapterview.getItemAtPosition(position);
                String poss=pos.getString(pos.getColumnIndexOrThrow("_id"));
                Intent produit = new Intent(getApplicationContext(), ListeProduits.class);
                produit.putExtra("position",poss);
                startActivity(produit);
            }
        });


    }
}
