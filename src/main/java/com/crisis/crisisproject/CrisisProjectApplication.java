package com.crisis.crisisproject;

import com.crisis.crisisproject.manager.NotificationDMManager;
import com.crisis.crisisproject.model.Notification;
import com.crisis.crisisproject.model.Utilisateur;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CrisisProjectApplication {



    public static ArrayList<Utilisateur> listUtilisateurConnected = new ArrayList<Utilisateur>();

    public static void main(String[] args) {

        SpringApplication.run(CrisisProjectApplication.class, args);
    }

}
