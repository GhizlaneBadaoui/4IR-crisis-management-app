package com.crisis.crisisproject.controler;

import com.crisis.crisisproject.model.Utilisateur;
import com.crisis.crisisproject.services.UserManagementService;
import com.crisis.crisisproject.services.CrisisManagementService;
import com.crisis.crisisproject.services.DecisionTreeService;

import com.crisis.crisisproject.services.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.SocketException;
import java.net.UnknownHostException;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
    @Autowired
    private UserManagementService userServices;
    @Autowired
    private CrisisManagementService crisisService;

    @Autowired
    private DecisionTreeService dtService;


    @Autowired
    NotificationService notifServices;

    //
    @RequestMapping(method = RequestMethod.GET,value = "/decisionTree/{contenuMessage}")
    public String getAdviceFromDT(@PathVariable String contenuMessage){
        System.out.println("Voici le contenu du message de la notif pour laquelle on doit donner un conseil : "+contenuMessage);
        String response = "";
        try {
            response = this.dtService.adviceFromDT(contenuMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(" Voici la réponse envoyée :"+response);
        return  response;
    }

    @RequestMapping(method = RequestMethod.GET,value ="/connexion/{givenPassword}/{emailUser}")
    public Boolean getConnexionAnswer(@PathVariable String givenPassword,@PathVariable String emailUser){
        Boolean response = userServices.checkPassword(givenPassword,emailUser);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET,value ="/connexion/{emailUser}")
    public Utilisateur getUser(@PathVariable String emaiUser){
        Utilisateur user = userServices.getUserFromDB(emaiUser);
        return user;
    }


    //Pensez au @requestBody si jamais ca marche pas
    @RequestMapping(method = RequestMethod.POST,value = "/connexion/{email}/{passWord}/{fullName}/{idRole}")
    public Boolean inscription(@PathVariable String email,@PathVariable String passWord,@PathVariable String fullName, @PathVariable int idRole ){
        Utilisateur.Role role;
        Boolean answer = false;
        switch (idRole){
            case 2:
                role = Utilisateur.Role.TEACHER;
                break;

            case 3:
                role = Utilisateur.Role.ADMINISTRATOR;
                break;

            default:
                role = Utilisateur.Role.STUDENT;
                break;
        }

        try {
            answer = userServices.addUser(email,passWord,fullName,role);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        return  answer;
    }

}
