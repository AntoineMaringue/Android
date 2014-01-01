/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNSTOCK;

/**
 *
 * @author antoi_000
 */
public class Produit {

    private String ID;
    private String marque;
    private String prix;
    private String name;
    private String contenance;
    private String imgSrc;
    private String categorie;
    private String unite;
    private String provenance;
    private String description;
    private String produits_stock;
    private String dlu;
    private String ddp;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getPrix() {
        return this.prix;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContenance(String contenance) {
        this.contenance = contenance;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getName() {
        return name;
    }

    public String getContenance() {
        return contenance;
    }

    public String getMarque() {
        return marque;
    }

    public String getCategorie() {
        return categorie;
    }

    @Override
    public String toString() {
        String informations = description != null ? description + "\n" : "" 
                + provenance != null ? provenance : ""
                + marque != null ? marque + "\n" : "" 
                + prix != null ? prix + "\n" : "" 
                + name != null ? name + "\n" : "" 
                + contenance != null ? contenance + unite + "\n" : "";
        return informations;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public String getUnite() {
        return unite;
    }

    public String getProvenance() {
        return provenance;
    }

    public String getDescription() {
        return description;
    }

    public String getProduits_stock() {
        return produits_stock;
    }

    public String getDlu() {
        return dlu;
    }

    public String getDdp() {
        return ddp;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProduits_stock(String produits_stock) {
        this.produits_stock = produits_stock;
    }

    public void setDlu(String dlu) {
        this.dlu = dlu;
    }

    public void setDdp(String ddp) {
        this.ddp = ddp;
    }
}
