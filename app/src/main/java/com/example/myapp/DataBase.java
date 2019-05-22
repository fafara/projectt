package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class DataBase {

    public static final int DB_VERSION = 6;
    public static final String DB_NAME = "project.db";
    ArrayList<Compte> liste = new ArrayList<Compte>( );
    Compte compte;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

//******************************** Create table , Insert into ********************************************//

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE IF NOT EXISTS PARAMETRE_GENERAL(ID_paramgeneral INTEGER PRIMARY KEY autoincrement, NombreDecimaleApresVirgule, HeureTelecollecteAutomatique,NomTpe, DeltaMinuteEnvoiTransaction ,NumeroTelecollecte) ");


            db.execSQL("CREATE TABLE IF NOT EXISTS TERMINAL (ID_terminal INTEGER PRIMARY KEY autoincrement,Num_serie_tpe TEXT,  numerolotticket TEXT , DateClôture , HeureClôture)");
            db.execSQL("INSERT INTO Terminal (ID_terminal)VALUES('0000000000')");


            db.execSQL(" CREATE TABLE IF NOT EXISTS UTILISATEUR (ID_utilisateur INTEGER PRIMARY KEY autoincrement, nom TEXT , prenom TEXT ,rôle TEXT, login TEXT, password TEXT , flag INTEGR )");
            db.execSQL("INSERT INTO UTILISATEUR ( nom , prenom , rôle, login , password , flag ) values ('test1','test1','Commerçant','test1','test1','0')");
            db.execSQL("INSERT INTO UTILISATEUR ( nom , prenom, rôle , login , password , flag ) values ('test2','test2','Vendeur','test2','test2','0')");


            db.execSQL(" CREATE TABLE IF NOT EXISTS PRODUIT (ID_produit INTEGER PRIMARY KEY autoincrement, designation TEXT, prixHT REAL , tauxTVA INTEGER , prixTTC REAL , imageblob BLOB , nom_categorie , ID_categorie INTEGER,FOREIGN KEY (ID_categorie) REFERENCES CATEGORIE (_id))");


            db.execSQL(" CREATE TABLE IF NOT EXISTS CATEGORIE (_id INTEGER PRIMARY KEY autoincrement, nomcategorie TEXT)");


            db.execSQL("CREATE TABLE IF NOT EXISTS TRANSACTIONS (ID_transaction INTEGER NOT NULL PRIMARY KEY autoincrement,type_transaction  TEXT ,num_ticket ,Date_tr ,Heure_tr , numero_autorisation)");


            //db.execSQL("CREATE TABLE IF NOT EXISTS TICKET (ID_ticket INTEGER PRIMARY KEY autoincrement, Numticket TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS COMMANDE (ID_commande INTEGER NOT NULL PRIMARY KEY autoincrement , DateCommande  , HeureCommande , Montant , Etat ,ID_user ,FOREIGN KEY (ID_user) REFERENCES UTILISATEUR (ID_utilisateur))");


            db.execSQL("CREATE TABLE IF NOT EXISTS PRODUIT_COMMANDE (ID_prodcommande INTEGER PRIMARY KEY autoincrement, IdCategorie ,nomCat ,IdProduit, Designation, Quantite , prixHT, tva , prixTTC , pTot , ID_cmd ,FOREIGN KEY (ID_cmd) REFERENCES PRODUIT_COMMANDE (ID_commande))");


            db.execSQL("CREATE TABLE IF NOT EXISTS AFFILIE (ID_commande INTEGER PRIMARY KEY autoincrement, Adresse TEXT , IdTerminal , nomPOS TEXT , DateDernierParam)");
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS PARAMETRE_GENERAL");
            db.execSQL("DROP TABLE IF EXISTS TERMINAL");
            db.execSQL("DROP TABLE IF EXISTS UTILISATEUR");
            db.execSQL("DROP TABLE IF EXISTS PRODUIT");
            db.execSQL("DROP TABLE IF EXISTS CATEGORIE");
            db.execSQL("DROP TABLE IF EXISTS TRANSACTIONS");
            db.execSQL("DROP TABLE IF EXISTS PRODUIT_COMMANDE");
            db.execSQL("DROP TABLE IF EXISTS AFFILIE");
            db.execSQL("DROP TABLE IF EXISTS COMMANDE");


            //db.execSQL("DROP TABLE IF EXISTS TICKET");


            onCreate(db);
        }
    }

    private final Context c;
    private DatabaseHelper dbhelper;
    private SQLiteDatabase sqldb;

    public DataBase(Context context) {
        this.c = context;
    }

    public DataBase open() throws SQLException {

        dbhelper = new DatabaseHelper(c);
        sqldb = dbhelper.getWritableDatabase( );
        return this;

    }
//**************************************** Functions *****************************************//


//**************************************** ADD USER ACCOUNT *******************************//

    public void insertUser(String nom, String prenom, String rôle, String login, String password) {
        sqldb.close( );
        sqldb = dbhelper.getWritableDatabase( );
        sqldb.execSQL("INSERT INTO UTILISATEUR (nom , prenom , rôle, login , password) VALUES('" + nom + "','" + prenom + "','" + rôle + "','" + login + "','" + password + "')");
    }


//**************************************** DELETE USER ACCOUNT *******************************//


    public void delete_user_account(String iduser) {
        sqldb.execSQL("DELETE FROM UTILISATEUR where ID_utilisateur='" + iduser + "'");
    }


    public boolean del(int id) {

        try {
            sqldb = dbhelper.getWritableDatabase( );
            int rows = sqldb.delete("UTILISATEUR", "ID_utilisateur=" + id, new String[]{String.valueOf(id)});
            sqldb.close( );
            return rows > 0;
        } catch (Exception e) {
            return false;
        }
    }

//**************************************** UPDATE USER ACCOUNT *******************************//

    public boolean updateUser(Compte compte) {
        ContentValues contentValues = new ContentValues( );
        contentValues.put("nom", compte.getNom( ));
        contentValues.put("prenom", compte.getPrenom( ));
        contentValues.put("login", compte.getLogin( ));
        contentValues.put("password", compte.getMdp( ));

        return (sqldb.update("UTILISATEUR", contentValues, "id=" + compte.getId_user( ), null)) > 0;

    }


    public void update_user_account(String id, String nom, String prenom, String role, String login, String mdp) {
        sqldb.execSQL("UPDATE UTILISATEUR SET  nom='" + nom + "', prenom = '" + prenom + "', rôle ='" + role + "', login ='" + login + "', password ='" + mdp + "' WHERE ID_utilisateur='" + id + "'");
    }


//******************************************** SELECT ID USER*******************************************//

    public Integer selectIdUser(String login) {
        sqldb.close( );
        Integer id = 0;
        sqldb = dbhelper.getWritableDatabase( );
        Cursor data = sqldb.rawQuery("SELECT ID_utilisateur FROM UTILISATEUR where login='" + login + "'", null);
        if (data.moveToNext( )) {
            id = data.getInt(0);
        }
        return id;
    }

//***************************************** ADD NEW PRODUCT *****************************************//

    public void insertProduct(String des, float ht, int tva, float ttc, byte[] image, String nom_cat, int id_cat) {
        ContentValues cv = new ContentValues( );

        cv.put("designation", des);
        cv.put("prixHT", ht);
        cv.put("tauxTVA", tva);
        cv.put("prixTTC", ttc);
        cv.put("imageblob", image);
        cv.put("nom_categorie", nom_cat);
        cv.put("ID_categorie", id_cat);

        // sqldb.execSQL("INSERT INTO PRODUIT (designation , reference , prixHT , tauxTVA , prixTTC, imageblob) VALUES ('"+des+"','"+ref+"','"+ht+"','"+tva+"','"+ttc+"',cv)");

        dbhelper.getWritableDatabase( ).insert("PRODUIT", null, cv);
    }


    //**************************************** LIST PRODUCTS *****************************************//

    //********************* PROCUCT BY CATEGORY *******************//

    public Cursor ListProd(Integer idcat) {
        sqldb.close( );
        sqldb = dbhelper.getWritableDatabase( );
        Cursor data = sqldb.rawQuery("SELECT * FROM PRODUIT where ID_categorie=" + idcat, null);
        return data;
    }


    public Cursor ListProduit() {

        Cursor data = sqldb.rawQuery("SELECT * FROM PRODUIT", null);
        if (data != null) {
            data.moveToFirst( );
        }
        return data;
    }


   /* public Cursor listPro() {
        sqldb = dbhelper.getWritableDatabase( );
        sqldb.rawQuery("SELECT PRODUIT BY CATEGORIE (ID_cat)");
        Cursor data = sqldb.rawQuery("SELECT * FROM PRODUIT WHERE ID_categorie = ID_cat")
        return data ;
    }*/


  /*  public Cursor ListProduit(String x) {
        sqldb.close( );
        sqldb = dbhelper.getWritableDatabase( );
        Cursor data = sqldb.rawQuery("SELECT * FROM PRODUIT Where nom_categorie='" + x + "'", null);
        return data;
    }*/


//******************************************** SELECT ID PRODUCT *******************************************//


    public Integer selectIdProd(String text) {
        sqldb.close( );
        Integer id = 0;
        sqldb = dbhelper.getWritableDatabase( );
        Cursor data = sqldb.rawQuery("SELECT ID_produit FROM PRODUIT where designation='" + text + "'", null);
        if (data.moveToNext( )) {
            id = data.getInt(0);
        }
        return id;
    }


    //**************************************** PASSER UNE COMMANDE *****************************************//


    public List<Produit> Select_ProduitInfosbyID(String idProd) {
        sqldb = dbhelper.getWritableDatabase( );
        String request = "SELECT * FROM PRODUIT where ID_produit='" + idProd + "'";
        Cursor row = sqldb.rawQuery(request, null);

        List<Produit> ListProduits = new ArrayList<Produit>( );
        if (row.moveToFirst( )) {
            do {
                Produit prds = new Produit( );
                prds.id_prod = row.getInt(0);
                prds.designation = row.getString(1);
                prds.prixHT = row.getInt(2);
                prds.tva = row.getInt(3);
                prds.prixTTCC = row.getFloat(4);
                prds.nomCat = row.getString(6);
                prds.idCat = row.getInt(7);

                ListProduits.add(prds);
            } while (row.moveToNext( ));
        }
        sqldb.close( );
        return ListProduits;
    }


//**************************************** INSERT NEW COMMAND *****************************************//


    public void InsererProduitCommander(ProduitCommandé pc) {

        sqldb = dbhelper.getWritableDatabase( );
        sqldb.execSQL("INSERT INTO PRODUIT_COMMANDE (IdCategorie ,nomCat ,IdProduit, Designation, Quantite , prixHT, tva , prixTTC)" + " VALUES('" + pc.getIdCategorie( ) + "','" + pc.getNomCategorie( ) + "','" + pc.getIdProduit( ) + "','" + pc.getDesignation( ) + "','" + pc.getQuantite( ) + "','" + pc.getPrixHT( ) + "','" + pc.getTva( ) + "','" + pc.getPrixTTC( ) + "')");
        sqldb.close( );
    }


    public void insertCmd(String DateCommande, String HeureCommande, float Montant, String id_user) {

        sqldb = dbhelper.getWritableDatabase( );
        sqldb.execSQL("INSERT INTO COMMANDE (DateCommande , HeureCommande ,Montant , ID_user) VALUES('" + DateCommande + "','" + HeureCommande + "','" + Montant + "','" + id_user + "')");
        sqldb.close( );
    }


//**************************************** PANIER *****************************************//

    public Cursor listeProdCommandé() {

        Cursor data = sqldb.rawQuery("SELECT * FROM PRODUIT_COMMANDE", null);
        if (data != null) {
            data.moveToFirst( );
        }
        return data;
    }


    //**************************************** ADD NEW CATEGORIE **************************************//
    public void insertCategorie(String x) {
        sqldb.close( );
        sqldb = dbhelper.getWritableDatabase( );
        sqldb.execSQL("INSERT INTO CATEGORIE (nomcategorie) VALUES ('" + x + "')");
    }

//********************************************** SPINNER CATEGORIE **************************************//

    public List<String> SelectCategorie() {
        sqldb = dbhelper.getWritableDatabase( );
        List<String> results = new ArrayList<String>( );
        String request = "SELECT nomcategorie FROM CATEGORIE ";
        Cursor c = sqldb.rawQuery(request, null);
        //************* Now iterate with cursor

        if (c.moveToFirst( )) {
            do {
                results.add(c.getString(0));

            } while (c.moveToNext( ));
        }
        c.close( );
        sqldb.close( );
        return results;
    }

//******************************************** SELECT ID CATEGORY*******************************************//


    public Integer selectIdCat(String text) {
        sqldb.close( );
        Integer id = 0;
        sqldb = dbhelper.getWritableDatabase( );
        Cursor data = sqldb.rawQuery("SELECT _id FROM CATEGORIE where nomcategorie='" + text + "'", null);
        if (data.moveToNext( )) {
            id = data.getInt(0);
        }
        return id;
    }


//********************************************** LIST CATEGORIES *********************************************//

    public Cursor ListCat() {

        Cursor data = sqldb.rawQuery("SELECT * FROM CATEGORIE", null);
        if (data != null) {
            data.moveToFirst( );
        }
        return data;
    }


//******************************************** LIST USERS *******************************************//

    public Cursor ListUser() {
        sqldb.close( );
        sqldb = dbhelper.getWritableDatabase( );
        Cursor data = sqldb.rawQuery("SELECT * FROM UTILISATEUR", null);
        return data;
    }


    public ArrayList<Compte> ListeUser() {

        Cursor data = sqldb.rawQuery("SELECT * FROM UTILISATEUR", null);
        if (data != null && data.getCount( ) > 0) {
            data.moveToFirst( );
            do {
                liste.add(new Compte(data.getInt(0), data.getString(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5)));


            } while (data.moveToNext( ));
        }

        return liste;
    }


    public List<Compte> listeUser() {
        try {
            List<Compte> comptes = new ArrayList<Compte>( );
            sqldb = dbhelper.getWritableDatabase( );

            Cursor data = sqldb.rawQuery("SELECT * FROM UTILISATEUR", null);
            if (data.moveToFirst( )) {
                do {
                    Compte compte = new Compte( );
                    compte.setId_user(data.getInt(0));
                    compte.setNom(data.getString(1));
                    compte.setPrenom(data.getString(2));
                    compte.setRole(data.getString(3));
                    compte.setLogin(data.getString(4));
                    compte.setMdp(data.getString(5));

                    comptes.add(compte);

                } while (data.moveToNext( ));
            }

            sqldb.close( );
            return comptes;
        } catch (Exception e) {
            return null;
        }
    }


    public Compte parID(int position) {
        Cursor data = sqldb.rawQuery("SELECT * FROM UTILISATEUR", null);
        data.moveToPosition(position);

        compte = new Compte(data.getInt(0), data.getString(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5));

        return compte;
    }

    //**************************************** AUTHENTIFICATION **************************************//
    public String valider(String login, String password) {
        String query = "SELECT * FROM UTILISATEUR WHERE login='" + login + "' and password='" + password + "'";
        Cursor row = sqldb.rawQuery(query, null);
        String e = "";
        if (row != null) {
            row.moveToFirst( );
            e = row.getString(3);
        }
        return e;
    }


//**************************************** TERMINAL *****************************************//

    public String Select_IDTerminal() {
        sqldb = dbhelper.getWritableDatabase( );
        String d = new String( );
        String request = "SELECT ID_terminal FROM TERMINAL";
        Cursor row = sqldb.rawQuery(request, null);

        if (row.getCount( ) == 0) {

            d = "0000000000";
        } else {

            if (row.moveToFirst( )) {
                d = row.getString(0);
            }
        }

        row.close( );
        sqldb.close( );
        return d;
    }


    public int GetMontantProduitsCommander() {

        sqldb = dbhelper.getWritableDatabase( );
        int d = 0;
        String request = "Select ptotal (prixTTC) FROM PRODUIT_COMMANDE";
        Cursor row = sqldb.rawQuery(request, null);

        if (row.moveToFirst( )) {
            d = row.getInt(0);
        }

        row.close( );
        sqldb.close( );
        return d;
    }


}



