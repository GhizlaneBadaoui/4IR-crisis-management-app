package com.crisis.crisisproject.model;

import com.crisis.crisisproject.CrisisProjectApplication;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.net.DatagramSocket;
import java.net.SocketException;

@Entity
@Table(name="Utilisateur")
public class Utilisateur {


    private String fullName;


    public String email;

    private String passWord;

    private Boolean userSafe;


    private  String role;


    public void addUserToConnectedList(){
        if(CrisisProjectApplication.listUtilisateurConnected != null){
            CrisisProjectApplication.listUtilisateurConnected.add(this);
        }
        else{
            System.out.println("La liste n'est pas initialisé");
        }

    }

    public void removeUserFromConnectedList(){
        if(CrisisProjectApplication.listUtilisateurConnected != null){
            CrisisProjectApplication.listUtilisateurConnected.remove(this);
        }
        else{
            System.out.println("La liste n'est pas initialisé");
        }

    }


    //Pour créer un Utilisateur sans attributs initialisés
    public Utilisateur()  {}

    public Utilisateur(String fullName) {
        super();
        this.fullName = fullName;
    }


    //Pour créer une instance d'utilisateur quand on voudra récupérér un Utilisateur depuis la base de données
    public Utilisateur(String email , String passWord) {
        super();
        this.email = email;
        this.passWord = passWord;

    }


    //Constructeur pour Créer une nouvelle instance de Utilisateur afin de rajouter dans la BD
    public Utilisateur(String fullName,String email,String password,String role) throws SocketException {
        super();
        this.fullName = fullName;
        this.email = email;
        this.passWord = password;
        this.userSafe = true;
        this.role = role;

    }

    @Id
    @Column(name="fullName")
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Id
    @Column(name="role")
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }


    @Id
    @Column(name="userSafe")
    public Boolean getUserSafe() {
        return userSafe;
    }

    public void setUserSafe(Boolean safe) {
        userSafe = safe;
    }


    @Id
    @Column(name="email")
    public  String getEmail(){return this.email;}

    public void setEmail(String Newemail) {
        this.email = Newemail;
    }


    @Id
    @Column(name="passWord")
    public String getPassWord(){return this.passWord;}

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
