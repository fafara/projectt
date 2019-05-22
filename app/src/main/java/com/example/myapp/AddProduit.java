package com.example.myapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class AddProduit extends AppCompatActivity {

    DataBase mydb;
    EditText designation;
    EditText prixHTT;
    EditText tauxTVA;
    EditText prixTTCC;
    TextView cat;
    Button ajoutproduit;
    Button fromGallery;
    Button fromCamera;
    Spinner sp1;
    ImageView gallerybutton;
    public Bitmap photo;
    byte[] byteArray;
    String s;
    private static final int IMAGE_GAL_CODE = 1000;
    private static final int IMAGE_CAM_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produit);

        designation = (EditText) findViewById(R.id.editDesignation);
        prixHTT = (EditText) findViewById(R.id.editHT);
        tauxTVA = (EditText) findViewById(R.id.editTVA);
        prixTTCC = (EditText) findViewById(R.id.editTTC);
        sp1 = (Spinner) findViewById(R.id.spinnerCategorie);
        ajoutproduit = (Button) findViewById(R.id.btnAjoutProd);
        fromCamera = (Button) findViewById(R.id.btnfromcamera);
        fromGallery = (Button) findViewById(R.id.btnfromgallery);
        cat = (TextView) findViewById(R.id.editCat);
        gallerybutton = (ImageView) findViewById(R.id.imgImport);
        ArrayAdapter<String> dataAdapter;
        mydb = new DataBase(getApplicationContext( ));

        mydb.open( );


        //**************** Importer image ***************//

        fromGallery.setOnClickListener(new View.OnClickListener( ) {
            public void onClick(View v) {
                pickImageFromGallery( );
            }
        });


       /*/ fromCamera.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                pickImageFromCamera();
            }
        });/*/


        //************** Spinner Categorie **************//

        // Spinner Drop down elements
        List<String> categories = mydb.SelectCategorie( );
        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp1.setAdapter(dataAdapter);


        tauxTVA.addTextChangedListener(new TextWatcher( ) {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length( ) != 0) {
                    String STVA = tauxTVA.getText( ).toString( );
                    int ITVA = Integer.parseInt(STVA);

                    Float ITotalTVA = (ITVA * Float.valueOf(prixHTT.getText( ).toString( ))) / 100;

                    Float ITotalTTc = ITotalTVA + Float.valueOf(prixHTT.getText( ).toString( ));
                    String STotalTTc = String.format("%.3f", ITotalTTc);
                    prixTTCC.setText(STotalTTc);

                } else {
                    prixTTCC.setText("");

                }
            }
        });


        ajoutproduit.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                s = sp1.getSelectedItem( ).toString( );
                int tva = Integer.parseInt(tauxTVA.getText( ).toString( ));
                float prixHT = Float.parseFloat(prixHTT.getText( ).toString( ));
                float prixTTC = Float.parseFloat(prixTTCC.getText( ).toString( ));
                String nom_cat = sp1.getSelectedItem( ).toString( );
                String des = designation.getText( ).toString( );
                int id = mydb.selectIdCat(s);

                if (des.equals("")) {
                    designation.setError("Veuillez saisir la Designation du produit ");
                } else if (prixHTT.getText( ).toString( ).equals("")) {
                    prixHTT.setError("Veuilez saisir la Reference du produit");
                } else if (tauxTVA.getText( ).toString( ).equals("")) {
                    tauxTVA.setError("Veuilez saisir la Reference du produit");
                } else if (prixTTCC.getText( ).toString( ).equals("")) {
                    prixTTCC.setError("Veuilez saisir la Reference du produit");
                } else {


                    mydb.insertProduct(des, prixHT, tva, prixTTC, byteArray, nom_cat, id);


                    Toast msgLoginError = Toast.makeText(getApplicationContext( ), "Produit ajoutée avec succès", Toast.LENGTH_LONG);
                    msgLoginError.show( );
                    designation.setText("");
                    tauxTVA.setText("");
                    prixHTT.setText("");
                    prixTTCC.setText("");
                    gallerybutton.setImageResource(R.drawable.importer);

                }
            }
        });
    }

    private byte[] imageViewToByte(ImageView gallerybutton) {
        Bitmap bitmap = ((BitmapDrawable) gallerybutton.getDrawable( )).getBitmap( );
        ByteArrayOutputStream stream = new ByteArrayOutputStream( );
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray( );
        return byteArray;
    }

    private void pickImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, IMAGE_GAL_CODE);
    }


    private void pickImageFromCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.setType("image/*");
        startActivityForResult(camera, IMAGE_CAM_CODE);
    }

// permission d'autorisation gallery

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_GAL_CODE) {
            //set image to ImageView
            gallerybutton.setImageURI(data.getData( ));

            Uri selectedImage = data.getData( );
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver( ).query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst( );
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close( );
            ImageView imageView = (ImageView) findViewById(R.id.imgImport);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            photo = BitmapFactory.decodeFile(picturePath);


            ByteArrayOutputStream stream = new ByteArrayOutputStream( );
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray( );


        }
        if (resultCode == RESULT_OK && requestCode == IMAGE_CAM_CODE) {
            gallerybutton.setImageURI(data.getData( ));


            // Bitmap photo = (Bitmap) data.getExtras().get("data");
            // gallerybutton.setImageBitmap(photo);


        }
    }
}




