package Entity;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp halim
 */
public class Voiture {
    private String matricule;
    private String nom;
    private String image;
    private String nomch;
    private String prix;
    private String information;
    private String type;

    public Voiture() {
    }

    public Voiture(String matricule, String nom, String image, String nomch, String prix, String information, String type) {
        this.matricule = matricule;
        this.nom = nom;
        this.image = image;
        this.nomch = nomch;
        this.prix = prix;
        this.information = information;
        this.type = type;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNomch() {
        return nomch;
    }

    public void setNomch(String nomch) {
        this.nomch = nomch;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Voiture{" + "matricule=" + matricule + ", nom=" + nom + ", image=" + image + ", nomch=" + nomch + ", prix=" + prix + ", information=" + information + ", type=" + type + '}';
    }
    
}
