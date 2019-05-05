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
public class Region {

    private int id;
    private String nom;
    private String code;
    private String payid;

    public Region() {
    }

    public Region(int id, String nom, String code, String payid) {
        this.id = id;
        this.nom = nom;
        this.code = code;
        this.payid = payid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    @Override
    public String toString() {
        return "Region{" + "id=" + id + ", nom=" + nom + ", code=" + code + ", payid=" + payid + '}';
    }
    
}
