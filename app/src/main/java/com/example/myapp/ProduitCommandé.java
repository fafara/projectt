package com.example.myapp;

public class ProduitCommandé {

    public String id_commande;
    public String idUser;
    public String idCategorie;
    public String nomCategorie;
    public String idProduit;
    public String Designation;
    public String Quantite;

    public int tva;
    public float prixHT;
    public float prixTTC;
    public String pTot ;


    public ProduitCommandé(){

    }

    public ProduitCommandé (String designation, String Quantite , float prixTTC , String pTot) {

        // this.id_cat = id_cat;
        this.Designation = designation;
        this.Quantite = Quantite;
        this.prixTTC = prixTTC;
        this.pTot = pTot;
    }



    public String getId_commande() {
        return id_commande;
    }

    public void setId_commande(String id_commande) {
        this.id_commande = id_commande;
    }

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getQuantite() {
        return Quantite;
    }

    public void setQuantite(String quantite) {
        Quantite = quantite;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public float getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(float prixTTC) {
        this.prixTTC = prixTTC;
    }

    public String getpTot() {
        return pTot;
    }

    public void setpTot(String pTot) {
        this.pTot = pTot;
    }
}
