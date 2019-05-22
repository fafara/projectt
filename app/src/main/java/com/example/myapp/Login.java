package com.example.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button btncnx ;
    EditText editMdp;
    EditText editUser;
    DataBase mydb;
    RelativeLayout relative1 ;
    Handler handler = new Handler();
    Runnable runnable = new Runnable( ) {
        @Override
        public void run() {
            relative1.setVisibility(View.VISIBLE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editMdp=(EditText)findViewById(R.id.editMdp);
        editUser=(EditText)findViewById(R.id.editUser);
        btncnx=(Button)findViewById(R.id.btnCon);
        relative1=(RelativeLayout) findViewById(R.id.relative1);
        mydb=new DataBase(getApplicationContext());
        mydb.open();

        handler.postDelayed(runnable, 2000);

        btncnx.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                if (editUser.getText().toString( ).equals("")) {
                    editUser.setError("Veuillez saisir votre Login");
                }else if(editMdp.getText().toString().equals("")){

                    editMdp.setError("Veuilez saisir votre MDP");
                }else {


                    String x = editUser.getText( ).toString( );
                    String y = editMdp.getText( ).toString( );

                    Intent i = new Intent(Login.this, ListeProduits.class);
                    String login = null;
                    i.putExtra("x", login);

                    String r = mydb.valider(x, y);

                    if (r.equals("")) {
                        Toast tt = Toast.makeText(getApplicationContext( ), "Login et mot de passe ne correspondent pas", Toast.LENGTH_LONG);
                        tt.show( );
                    } else if (r.equals("Commerçant")) {
                        Intent other = new Intent(getApplicationContext( ), MenuCommercant.class);
                        startActivity(other);
                    } else {
                        Intent o = new Intent(getApplicationContext( ), MenuVendeur.class);
                        startActivity(o);
                    }

                }
            }
        });


    }
}
