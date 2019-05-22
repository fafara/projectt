package com.example.myapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class UpdateUser extends AppCompatActivity {

    private TextView nom ;
    private TextView prenom;
    private TextView role;
    private TextView login ;
    private TextView mdp ;
    private Button update ;
    private Button supprimer ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user);

        Intent intent = getIntent();
        final Compte compte = (Compte) intent.getSerializableExtra("compte");

        this.nom = (TextView) findViewById(R.id.modifNom);
        this.nom.setText(compte.getNom());

        this.prenom = (TextView) findViewById(R.id.modifPr);
        this.prenom.setText(compte.getPrenom());

        role = (TextView) findViewById(R.id.modifRole);
        this.role.setText(compte.getRole());

        this.login = (TextView) findViewById(R.id.modifLogin);
        this.login.setText(compte.getLogin());

        this.mdp = (TextView) findViewById(R.id.modifMDP);
        this.mdp.setText(compte.getMdp());

        this.update = (Button) findViewById(R.id.sauvuser);
        this.supprimer = (Button) findViewById(R.id.supp);

        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Rôle, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(adapter);*/


        supprimer.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateUser.this);

                builder.setCancelable(false);
                builder.setTitle("Confirmation");

                builder.setMessage("Vous êtes sur ?");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        final DataBase mydb = new DataBase(getBaseContext());
                            mydb.open();
                            mydb.del (compte.getId_user()) ;

                            Intent intent1= new Intent(UpdateUser.this, ListeUsers.class);
                            startActivity(intent1);

                        }});
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        dialog.cancel();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });











    }
}
