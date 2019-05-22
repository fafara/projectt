package com.example.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView start , store ;
    Animation frombottom , fromtop ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (ImageView) findViewById(R.id.start);
        store = (ImageView) findViewById(R.id.store);
        frombottom = AnimationUtils.loadAnimation(this , R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this , R.anim.fromtop);


        start.setAnimation(frombottom);

        store.setAnimation(fromtop);

        start.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , Login.class);
                startActivity(i);
            }
        });

    }
}
