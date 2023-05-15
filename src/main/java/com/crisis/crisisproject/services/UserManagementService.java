package com.crisis.crisisproject.services;

import com.crisis.crisisproject.manager.UserDBManager;
import com.crisis.crisisproject.model.Utilisateur;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.net.UnknownHostException;

@Service
public class UserManagementService {

    public Utilisateur getUserFromDB(String email) {
        Utilisateur user = null;
        try {
            user = UserDBManager.getUtilisateur(email);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public Boolean checkPassword(String password, String email) {
        String realPasswordUser = null;
        Boolean answer = false;


      /*  realPasswordUser = UserDBManager.getUserPassword(email);

        if ((realPasswordUser != null) && (password.equals(realPasswordUser))) {
            answer = true;
        }

        System.out.println(" email : "+email+" password : "+password);*/
        answer = true;
        return answer;

    }


    public Boolean addUser(String emaiUser, String password, String fullName, Utilisateur.Role role) throws UnknownHostException, SocketException {

        Boolean answerCreation = false;
        Utilisateur newUser = new Utilisateur(fullName,emaiUser,password,role);

        Utilisateur userFromDB = UserDBManager.getUtilisateur(emaiUser);

        if (userFromDB.getEmail().equals("NOT FOUND")){
            UserDBManager.InsertDetached(newUser);
            answerCreation = true;
        }

        return answerCreation;
    }

}
