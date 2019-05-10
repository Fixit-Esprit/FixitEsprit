/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author abdelhalim.benjmila
 */
public class Position {
    private String ville;
    private String region;
    private String pays;
    private Double latitude;
    private Double longitude;

    public Position() {
    }

    public Position(String ville, String region, String pays, Double latitude, Double longitude) {
        this.ville = ville;
        this.region = region;
        this.pays = pays;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = (int) (29 * hash + this.latitude);
        hash = (int) (29 * hash + this.longitude);
        return hash;
    }

    @Override
    public String toString() {
        return "Position{" + "ville=" + ville + ", region=" + region + ", pays=" + pays + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }

    
}
