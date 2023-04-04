package app.crisis_management.controller;

import app.crisis_management.model.User;
import app.crisis_management.service.CrisisManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
    @Autowired
    private CrisisManagementService crisisService;

    @RequestMapping(method = RequestMethod.GET, value = "/hi")
    public User getUser() {
        return crisisService.getUser();
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hi :) ";
    }
}
