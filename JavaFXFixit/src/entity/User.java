/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author bhk
 */
public class User {
    
    private int id,nbPoint, ville,pays,region;
    private String nom, prenom, login,pwd,telephone,email,image,adresse;

    public User(int id, int nbPoint, String nom, String prenom, String adresse, String login, String pwd, String telephone, String email, String image,int ville,int pays,int region) {
        this.id = id;
        this.nbPoint = nbPoint;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.login = login;
        this.pwd = pwd;
        this.telephone = telephone;
        this.email = email;
        this.image = image;
        this.ville = ville;
        this.pays = pays;
        this.region = region;
    }
    public User(String nom, String prenom, String adresse, String login, String pwd, String telephone, String email, String image,int nbPoint,int ville) {
        
        this.nbPoint = nbPoint;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.login = login;
        this.pwd = pwd;
        this.telephone = telephone;
        this.email = email;
        this.image = image;        
        this.ville = ville;
    }

    public User(String login, String pwd) {
        this.login = login;
        this.pwd = pwd;
       
    }

    public int getId() {
        return id;
    }

    public int getNbPoint() {
        return nbPoint;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getLogin() {
        return login;
    }

    public String getPwd() {
        return pwd;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNbPoint(int nbPoint) {
        this.nbPoint = nbPoint;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getVille() {
        return ville;
    }

    public int getPays() {
        return pays;
    }

    public int getRegion() {
        return region;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(String image) {
        this.image = image;
    }
 

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse + '}';
    }
    
    
    
    
    
}
