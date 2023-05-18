package com.crisis.crisisproject.controller;


import com.crisis.crisisproject.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    private UserManagementService userServices;


    @RequestMapping(method = RequestMethod.GET,value ="/connexion/{givenPassword}/{emailUser}")
    public Boolean getConnexionAnswer(@PathVariable String givenPassword, @PathVariable String emailUser){
        Boolean response = userServices.checkPassword(emailUser,givenPassword);
        return response;
    }




}
