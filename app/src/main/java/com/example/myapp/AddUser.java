package com.example.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddUser extends AppCompatActivity {


    DataBase mydb;
    EditText nom;
    EditText prenom;
    EditText login;
    EditText mdp;
    EditText confirmdp;
    Spinner role;
    Button creer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        nom = (EditText) findViewById(R.id.txtNom);
        prenom = (EditText) findViewById(R.id.txtPrenom);
        role = (Spinner) findViewById(R.id.spinnerRôle);
        login = (EditText) findViewById(R.id.txtLogin);
        mdp = (EditText) findViewById(R.id.txtPassword);
        confirmdp = (EditText) findViewById(R.id.txtConfirmpass);
        creer = (Button) findViewById(R.id.btnCréerCompte);
        mydb = new DataBase(getApplicationContext( ));

        mydb.open( );

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Rôle, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(adapter);

        creer.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                InsererUser( );
            }
        });
    }

    public void InsererUser() {


        if (nom.getText( ).toString( ).equals("")) {
            nom.setError("Veuillez saisir votre nom");


        } else if (prenom.getText( ).toString( ).equals("")){
            prenom.setError("Veuilez saisir votre prenom");


        } else if (login.getText( ).toString( ).equals("")) {
            login.setError("Veuilez saisir votre login");


        } else if (mdp.getText( ).toString( ).equals("")) {
            mdp.setError("Veuilez saisir votre MDP");


        } else if (confirmdp.getText( ).toString( ).equals("")) {
            confirmdp.setError("Veuilez confirmer votre MDP");


        } else {



            if (mdp.getText( ).toString( ).equals(confirmdp.getText( ).toString( ))) {
                mydb.insertUser(nom.getText( ).toString( ), prenom.getText( ).toString( ), role.getSelectedItem( ).toString( ),
                        login.getText( ).toString( ), mdp.getText( ).toString( ));


                Toast msgLoginError = Toast.makeText(getApplicationContext( ), "Compte crée avec succès", Toast.LENGTH_LONG);
                msgLoginError.show( );

                nom.setText("");
                prenom.setText("");
                login.setText("");
                mdp.setText("");
                confirmdp.setText("");


            } else {

                Toast msgLoginError = Toast.makeText(getApplicationContext( ), "un erreur s'est produit", Toast.LENGTH_LONG);
                msgLoginError.show( );

            }
        }
    }
}