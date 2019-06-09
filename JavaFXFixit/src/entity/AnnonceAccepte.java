/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

/**
 *
 * @author abdelhalim.benjmila
 */
public class AnnonceAccepte {

    private int idannonce;
    private int idprestataire;
    private int prix;
    private String date;

    public AnnonceAccepte(int idannonce, int idprestataire, int prix, String date) {
        this.idannonce = idannonce;
        this.idprestataire = idprestataire;
        this.prix = prix;
        this.date = date;
    }

    public int getIdannonce() {
        return idannonce;
    }

    public void setIdannonce(int idannonce) {
        this.idannonce = idannonce;
    }

    public int getIdprestataire() {
        return idprestataire;
    }

    public void setIdprestataire(int idprestataire) {
        this.idprestataire = idprestataire;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.idannonce;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnnonceAccepte other = (AnnonceAccepte) obj;
        if (this.idannonce != other.idannonce) {
            return false;
        }
        if (this.idprestataire != other.idprestataire) {
            return false;
        }
        if (this.prix != other.prix) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AnnonceAccepte{" + "idannonce=" + idannonce + ", idprestataire=" + idprestataire + ", prix=" + prix + ", date=" + date + '}';
    }
    
    
}
