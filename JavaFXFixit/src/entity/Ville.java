/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author hphqlim
 */
public class Ville {

    private int id;
    private String regid;
    private String payid;
    private Double latitude;
    private Double longitude;
    private String nom;

    public Ville() {
    }

    public Ville(int id, String regid, String payid, Double latitude, Double longitude, String nom) {
        this.id = id;
        this.regid = regid;
        this.payid = payid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegid() {
        return regid;
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Ville{" + "id=" + id + ", regid=" + regid + ", payid=" + payid + ", latitude=" + latitude + ", longitude=" + longitude + ", nom=" + nom + '}';
    }
    
}
