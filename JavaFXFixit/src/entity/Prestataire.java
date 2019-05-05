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
public class Prestataire {
    private int id;
    private int adresse_id;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private String image;
    private int nbpiont;
    private String description;

    public Prestataire() {
    }

    public Prestataire(int id, int adresse_id, String nom, String prenom, String email, String tel, String image, int nbpiont, String description) {
        this.id = id;
        this.adresse_id = adresse_id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.image = image;
        this.nbpiont = nbpiont;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdresse_id() {
        return adresse_id;
    }

    public void setAdresse_id(int adresse_id) {
        this.adresse_id = adresse_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNbpiont() {
        return nbpiont;
    }

    public void setNbpiont(int nbpiont) {
        this.nbpiont = nbpiont;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Prestatair{" + "id=" + id + ", adresse_id=" + adresse_id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", tel=" + tel + ", image=" + image + ", nbpiont=" + nbpiont + ", description=" + description + '}';
    }

    @Override
    public int hashCode() {
        return this.id;
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
        final Prestataire other = (Prestataire) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
