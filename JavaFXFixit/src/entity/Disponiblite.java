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
public class Disponiblite {

    private int id;
    private int idPrestataire;
    private String Date;
    private int disponible;

    public Disponiblite() {
    }

    public Disponiblite(int id, int idPrestataire, String Date, int disponible) {
        this.id = id;
        this.idPrestataire = idPrestataire;
        this.Date = Date;
        this.disponible = disponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPrestataire() {
        return idPrestataire;
    }

    public void setIdPrestataire(int idPrestataire) {
        this.idPrestataire = idPrestataire;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Disponiblite{" + "id=" + id + ", idPrestataire=" + idPrestataire + ", Date=" + Date + ", disponible=" + disponible + '}';
    }
    
}
