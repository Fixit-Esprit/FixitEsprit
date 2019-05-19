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
public class Demande {
    private int id;
    private int idpristataire;
    private String nomprestataire;
    private int idclient;
    private String nomclient;
    private String adresseclient;
    private String description;
    private String service;
    private String image;
    private Date dateDemande;
    private String dateFunction;
    private int acceptation_prestataire;
    private Date dateAcceptation;
    private int acceptation_client;
    private int prix;

    public Demande() {
    }

    public Demande(int idpristataire, int idclient, String description, String image, Date dateDemande, String dateFunction) {
        this.idpristataire = idpristataire;
        this.idclient = idclient;
        this.description = description;
        this.image = image;
        this.dateDemande = dateDemande;
        this.dateFunction = dateFunction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdpristataire() {
        return idpristataire;
    }

    public void setIdpristataire(int idpristataire) {
        this.idpristataire = idpristataire;
    }

    public int getIdclient() {
        return idclient;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
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

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getDateFunction() {
        return dateFunction;
    }

    public void setDateFunction(String dateFunction) {
        this.dateFunction = dateFunction;
    }

    public int getAcceptation_prestataire() {
        return acceptation_prestataire;
    }

    public void setAcceptation_prestataire(int acceptation_prestataire) {
        this.acceptation_prestataire = acceptation_prestataire;
    }

    public Date getDateAcceptation() {
        return dateAcceptation;
    }

    public void setDateAcceptation(Date dateAcceptation) {
        this.dateAcceptation = dateAcceptation;
    }

    public int getAcceptation_client() {
        return acceptation_client;
    }

    public void setAcceptation_client(int acceptation_client) {
        this.acceptation_client = acceptation_client;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getNomprestataire() {
        return nomprestataire;
    }

    public void setNomprestataire(String nomprestataire) {
        this.nomprestataire = nomprestataire;
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
    
    @Override
    public String toString() {
        return "Demande{" + "id=" + id + ", idpristataire=" + idpristataire + ", idclient=" + idclient + ", description=" + description + ", image=" + image + ", dateDemande=" + dateDemande + ", dateFunction=" + dateFunction + ", acceptation_prestataire=" + acceptation_prestataire + ", dateAcceptation=" + dateAcceptation + ", acceptation_client=" + acceptation_client + ", prix=" + prix + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
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
        final Demande other = (Demande) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
