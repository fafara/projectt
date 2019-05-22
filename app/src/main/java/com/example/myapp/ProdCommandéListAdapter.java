package com.example.myapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class ProdCommandéListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<ProduitCommandé> listproduitCommandés;
    private int layout ;



    public ProdCommandéListAdapter(Context context, int layout, ArrayList<ProduitCommandé> produitCommandés) {
        this.context = context ;
        this.layout = layout ;
        this.listproduitCommandés = produitCommandés ;
    }

    @Override
    public int getCount() {
        return listproduitCommandés.size();
    }

    @Override
    public Object getItem(int position) {
        return listproduitCommandés.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder{

        TextView quantité , designation , prix , prixTot;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.quantité = (TextView) row.findViewById(R.id.qte);
            holder.designation = (TextView) row.findViewById(R.id.des);
            holder.prix = (TextView) row.findViewById(R.id.ttc);
            holder.prixTot = (TextView) row.findViewById(R.id.pt);


            row.setTag(holder);

        } else {
            holder = (ProdCommandéListAdapter.ViewHolder)row.getTag();
        }
        ProduitCommandé produit =listproduitCommandés.get(position);

        holder.quantité.setText(produit.getQuantite());

        //  float txtprix = Float.valueOf(produit.getprixTTCC());

        holder.designation.setText(produit.getDesignation());

        holder.prix.setText(String.valueOf(produit.prixTTC));
        int p= Integer.parseInt(produit.getQuantite());
        float ch=  produit.prixTTC*p;
        holder.prixTot.setText(String.valueOf(ch));

        return row;
    }
}
