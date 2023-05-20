package com.crisis.crisisproject.service;


import com.crisis.crisisproject.manager.UserDBManager;
import com.crisis.crisisproject.model.Utilisateur;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.net.UnknownHostException;

@Service
public class UserManagementService {






    public Boolean checkPassword(String email, String password) {
        Boolean answer = false;
        String realPasswordUser = null;
        Utilisateur userFromDB = null;

        try {
            userFromDB = UserDBManager.getUtilisateur(email);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
           e.printStackTrace();
        }

        if(userFromDB == null){
            System.out.println("From UserManagementService : le retour de getUtilisateur est null donc pb dans UserDBManager ");
            return answer;
        }
        else{
            if(userFromDB.getEmail().equals("null_1")){
                System.out.println("From UserManagementService : getUtilisateur renvoie un User mais son email est set a null");
                return answer;
            }
            else if(userFromDB.getEmail().equals("NOT FOUND")){
                System.out.println("From UserManagementService : getUtilisateur renvoie un User mais son email est set a NOT FOUND donc utilisateur pas trouvé dans la liste renvoyé par la db");
                return answer;
            }
            else{
                realPasswordUser = userFromDB.getPassWord();
            }
        }


        if ((realPasswordUser != null) && (password.equals(realPasswordUser))) {
            answer = true;
            userFromDB.addUserToConnectedList();
        }

        System.out.println(" email : "+email+" password : "+password);
        System.out.println("la réponse renvoyé est : "+answer);

        return answer;

    }



}
