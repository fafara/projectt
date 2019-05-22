package com.example.myapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ProduitListAdapter extends BaseAdapter  {

    private Context context;
    private int layout ;
    private ArrayList<Produit> listProduit ;


    public ProduitListAdapter(Context context, int layout, ArrayList<Produit> listProduit) {
        this.context = context;
        this.layout = layout;
        this.listProduit = listProduit;
    }


    @Override
    public int getCount() {
        return listProduit.size();
    }

    @Override
    public Object getItem(int position) {
        return listProduit.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder{

        ImageView imageView;
        TextView txtdes , txtprix ;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtdes = (TextView) row.findViewById(R.id.txtdes);
            holder.txtprix = (TextView) row.findViewById(R.id.txtprix);
            holder.imageView = (ImageView) row.findViewById(R.id.imgprod);

            row.setTag(holder);

        } else {
            holder = (ViewHolder)row.getTag();
        }
        Produit produit =listProduit.get(position);

        holder.txtdes.setText(produit.getDesignation());

    //  float txtprix = Float.valueOf(produit.getprixTTCC());

        holder.txtprix.setText(String.valueOf(produit.getprixTTCC()));

        byte[] gallerybutton = produit.getgallerybutton();
        Bitmap bitmap = BitmapFactory.decodeByteArray(gallerybutton, 0,gallerybutton.length);

        BitmapDrawable hh =  new BitmapDrawable(context.getResources(), bitmap);
        holder.imageView.setImageBitmap(bitmap);


    return row;
    }
}
