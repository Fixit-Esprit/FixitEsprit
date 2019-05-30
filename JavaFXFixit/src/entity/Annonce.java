/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

/**
 *
 * @author hphqlim
 */
public class Annonce {

    private int id;
    private int idclient;
    private int idservice;
    private Date date;
    private String description;
    private String image;
    private int minprix;
    private int maxprix;
    private String nomclient;
    private String adresseclient;
    private int prix;

    public Annonce(int idclient, int idservice, Date date, String description, String image, int minprix, int maxprix) {
        this.idclient = idclient;
        this.idservice = idservice;
        this.date = date;
        this.description = description;
        this.image = image;
        this.minprix = minprix;
        this.maxprix = maxprix;
    }

    public Annonce() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdclient() {
        return idclient;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

    public int getIdservice() {
        return idservice;
    }

    public void setIdservice(int idservice) {
        this.idservice = idservice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMinprix() {
        return minprix;
    }

    public void setMinprix(int minprix) {
        this.minprix = minprix;
    }

    public int getMaxprix() {
        return maxprix;
    }

    public void setMaxprix(int maxprix) {
        this.maxprix = maxprix;
    }

    public String getNomclient() {
        return nomclient;
    }

    public void setNomclient(String nomclient) {
        this.nomclient = nomclient;
    }

    public String getAdresseclient() {
        return adresseclient;
    }

    public void setAdresseclient(String adresseclient) {
        this.adresseclient = adresseclient;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Annonce{" + "id=" + id + ", idclient=" + idclient + ", idservice=" + idservice + ", date=" + date + ", description=" + description + ", image=" + image + ", minprix=" + minprix + ", maxprix=" + maxprix + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
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
        final Annonce other = (Annonce) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
