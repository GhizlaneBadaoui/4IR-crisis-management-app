package app.crisis_management.service;

import app.crisis_management.model.User;
import org.springframework.stereotype.Service;

@Service
public class CrisisManagementService {
    public User getUser() {
        return new User("Ghizlane", "BADAOUI", "****");
    }
}
