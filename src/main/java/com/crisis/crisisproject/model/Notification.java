package com.crisis.crisisproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Notification {

    public  String contenuAlerte;

    public String lieuAlerte;


    public String typeAlerte;

    private Timestamp timestamp;

    public String emailSender;

    public Notification(){}


    public Notification(String emailSender,String lieuAlerte, String typeAlerte , String contenueAlert){
        this.emailSender = emailSender;
        this.contenuAlerte = contenueAlert;
        this.typeAlerte = typeAlerte;
        this.lieuAlerte = lieuAlerte;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());

    }


    @Id
    @Column(name = "contenuNotif")
    public String getContenuAlerte() {
        return contenuAlerte;
    }

    public void setContenuAlerte(String contenuAlerte) {
        this.contenuAlerte = contenuAlerte;
    }

    @Id
    @Column(name = "lieuNotif")
    public String getLieuAlerte() {
        return lieuAlerte;
    }

    public void setLieuAlerte(String lieuAlerte) {
        this.lieuAlerte = lieuAlerte;
    }


    @Id
    @Column(name = "typeNotif")
    public String getTypeAlerte() {
        return typeAlerte;
    }

    public void setTypeAlerte(String typeAlerte) {
        this.typeAlerte = typeAlerte;
    }


    @Id
    @Column(name = "timestampNotif")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }



    @Id
    @Column(name = "senderNotif")
    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }
}
