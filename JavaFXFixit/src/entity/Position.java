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
    private Double latitude;
    private Double longitude;

    public Position() {
    }

    public Position(Double latitude, Double longitude) {
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = (int) (29 * hash + this.latitude);
        hash = (int) (29 * hash + this.longitude);
        return hash;
    }

    @Override
    public String toString() {
        return "Position{" + "latitude=" + latitude + ", longitude=" + longitude + '}';
    }
    
}
