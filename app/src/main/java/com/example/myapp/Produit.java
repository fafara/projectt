package com.example.myapp;

public class Produit {


    public String designation;
    public float prixTTCC;
    public byte[] gallerybutton;
    public int id_prod;
    public int tva;
    public float prixHT;
    public int idCat ;
    public String nomCat;



    public Produit(String designation, float prixTTCC, byte[] gallerybutton) {

        // this.id_cat = id_cat;
        this.designation = designation;
        this.prixTTCC = prixTTCC;
        this.gallerybutton = gallerybutton;

    }



    public Produit() {

    }

    public float getPrixTTCC() {
        return prixTTCC;
    }

    public void setPrixTTCC(float prixTTCC) {
        this.prixTTCC = prixTTCC;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public int getTva() {
        return tva;
    }

    public void setTva(int tva) {
        this.tva = tva;
    }

    public float getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(float prixHT) {
        this.prixHT = prixHT;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public float getprixTTCC() {
        return prixTTCC;
    }

    public void setprixTTCC(float prix) {
        this.prixTTCC = prix;
    }

    public byte[] getgallerybutton() {
        return gallerybutton;
    }

    public void setgallerybutton(byte[] image) {
        this.gallerybutton = image;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getNomCat() {
        return nomCat;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }
}
