package com.example.myapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class UserListAdapter extends ArrayAdapter<Compte> {

    private Context context;
    private List<Compte> comptes;
    Activity a ;

    public UserListAdapter(Context context, List<Compte> comptes) {
        super(context, R.layout.item_liste_accounts, comptes);

        this.context = context;
        this.comptes = comptes;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.item_liste_accounts, parent , false);

        TextView nom = (TextView) view.findViewById(R.id.nom);
        nom.setText(comptes.get(position).getNom());

        TextView prenom = (TextView) view.findViewById(R.id.prenom);
        prenom.setText(comptes.get(position).getPrenom());

       /*/ ImageView sup = (ImageView) view.findViewById(R.id.supprimeruser);
        sup.setTag(position);

        ImageView update = (ImageView) view.findViewById(R.id.modifieruser);
        update.setTag(position);/*/


        return view ;

    }
}











// row = layoutInflater.inflate(layout, null);

//holder.nom = (TextView) row.findViewById(R.id.nom);
//holder.prenom = (TextView) row.findViewById(R.id.prenom);

// holder.login = (TextView) row.findViewById(R.id.login);

//row.setTag(holder); }

        /*/else
            { holder = (ViewHolder)row.getTag(); }

            Compte compte =listCompteUtilisateur.get(position);

                holder.nom.setText(compte.getNom());
                holder.prenom.setText(compte.getPrenom());

            //holder.login.setText(compte.getLogin());

        return row; /*/

