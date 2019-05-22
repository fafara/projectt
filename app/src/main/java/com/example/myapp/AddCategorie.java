package com.example.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddCategorie extends AppCompatActivity {

    DataBase mydb;
    TextView nomcategorie ;
    Button ajoutercatégorie ;
    Button annulercategorie ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categorie);

        nomcategorie = (TextView) findViewById(R.id.txtCategorie);
        ajoutercatégorie = (Button) findViewById(R.id.btnAjcategorie);
        annulercategorie = (Button) findViewById(R.id.btnAnnulerCategorie);
        mydb=new DataBase(getApplicationContext());

        mydb.open();

        ajoutercatégorie.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
              String cat;
              cat = nomcategorie.getText().toString();


                if (cat.equals(""))
                {
                    nomcategorie.setError("Veuillez saisir le nom de la catégorie");

                } else {
                    mydb.insertCategorie(cat);
                    Toast msgLoginError = Toast.makeText(getApplicationContext( ), "Catégorie ajoutée avec succès", Toast.LENGTH_LONG);
                    msgLoginError.show( );
                    nomcategorie.setText("");
                }
            }
        });
    }




    }



