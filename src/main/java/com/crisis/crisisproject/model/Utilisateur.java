package com.crisis.crisisproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.net.DatagramSocket;
import java.net.SocketException;

@Entity
@Table(name="Utilisateur")
public class Utilisateur {


    private DatagramSocket datagramSocket;

    public enum Role {
        STUDENT,
        TEACHER,
        ADMINISTRATOR
    }

    private String fullName;


    public String email;

    private String passWord;

    private Boolean userSafe;


    private  Role role;



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
    public Utilisateur(String fullName,String email,String password,Role role) throws SocketException {
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
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
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
    public  String getEmail(){return this.passWord;}

    public void setEmail(String email) {
        this.email = email;
    }


    @Id
    @Column(name="passWord")
    public String getPassWord(){return this.passWord;}

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
