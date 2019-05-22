package com.example.myapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class ListProdModifier extends AppCompatActivity {

    DataBase mydb;
    ImageView AddProduct , deleteProd , updateProd;
    //SimpleCursorAdapter adapter;
    ListView gridView;
    ArrayList<Produit> liste;
    ProduitListAdapter adapter = null;
    //EditText userInputDialogEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_prod_modifier);

        gridView = (ListView) findViewById(R.id.gridview);
        liste = new ArrayList<>( );

        mydb = new DataBase(getApplicationContext( ));
        mydb.open( );

        AddProduct = findViewById(R.id.addProd);

        AddProduct.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListProdModifier.this, AddProduit.class);
                startActivity(intent);
            }
        });

        Cursor data = mydb.ListProduit();


        if (data.getCount( ) == 0) {
            Toast.makeText(ListProdModifier.this, "Il n'existe aucun produit", Toast.LENGTH_SHORT).show( );
        } else {
            while (data.moveToNext( )) {

                String designation = data.getString(1);
                float prixTTCC = data.getFloat(4);
                byte[] gallerybutton = data.getBlob(5);

                liste.add(new Produit(designation, prixTTCC, gallerybutton));
            }


            adapter = new ProduitListAdapter(this, R.layout.item_prod_modif, liste);
            gridView.setAdapter(adapter);

        }




    }
}
