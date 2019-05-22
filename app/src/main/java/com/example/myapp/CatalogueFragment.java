package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import java.util.zip.Inflater;



public class CatalogueFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_catalogue,container, false);

        Button  categorie = (Button)view.findViewById(R.id.btnCategorie);
        Button produit = (Button)view.findViewById(R.id.btnProduit);

        categorie.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent cat = new Intent(getContext(),ListCatModifier.class);
                startActivity(cat);
            }
        });



        produit.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent prod = new Intent(getContext(),ListProdModifier.class);
                startActivity(prod);
            }
        });




        return view;

    }




}
