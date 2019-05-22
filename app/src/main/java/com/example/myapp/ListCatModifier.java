package com.example.myapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ListCatModifier extends AppCompatActivity {

    DataBase mydb;
    ListView liste;
    ImageView AddCategory, deleteCat, updateCat;
    SimpleCursorAdapter adapter;
    ArrayAdapter<String> listeCategorieAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_categorie_modif);

        liste = findViewById(R.id.liste_cat);
        AddCategory = findViewById(R.id.addCat);
        mydb = new DataBase(this);
        mydb.open( );

        AddCategory.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListCatModifier.this, AddCategorie.class);
                startActivity(intent);
            }
        });


      /*  listeCategorieAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.item_cat_modif);


        Cursor data = mydb.ListeCat();

        if ( data.getCount() == 0){
            Toast.makeText(ListCatModifier.this, "Base de données est vide", Toast.LENGTH_SHORT).show( );
        } else {

            while(data.moveToNext()){
                listeCategorieAdapter.add(data.getString(1));
                // ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listeAccountsAdapter);
                liste.setAdapter(listeCategorieAdapter);

            }*/

        int layouList=    R.layout.item_cat_modif;
        getIntent();
        int[] xml_id=new int[]{
                R.id.categorie    };
        String[] colmn=new String[]{
                "nomcategorie"  };
        Cursor data = mydb.ListCat();

        if ( data.getCount() == 0){
            Toast.makeText(ListCatModifier.this, "Base de données est vide", Toast.LENGTH_SHORT).show( );
        } else {

            /*while(data.moveToNext()){
                listeCategorieAdapter.add(data.getString(1));
                // ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listeAccountsAdapter);
                liste.setAdapter(listeCategorieAdapter);*/

            adapter=new SimpleCursorAdapter(this,layouList,data,colmn,xml_id,0);
            liste.setAdapter(adapter);
        }



    }
}
