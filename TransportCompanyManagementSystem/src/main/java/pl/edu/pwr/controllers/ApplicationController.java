package pl.edu.pwr.controllers;

import pl.edu.pwr.Repositories.UserRepository;
import pl.edu.pwr.models.User;
import pl.edu.pwr.views.application.Index;

public class ApplicationController {
    private final Index indexView;
    private final UserRepository userRepository;

    public ApplicationController() {
        this.userRepository = new UserRepository();
        this.indexView = new Index();
    }

    public void runApp() {
        String username = indexView.initialize();
        User user = userRepository.getByUsername(username);

        switch (user.getUserType()) {
            case CLIENT -> new ClientController(user).index();
            case ADMINISTRATOR -> new AdministratorController(user).index();
            case DRIVER -> new DriverController(user).index();
            default -> {
                System.out.println("Niepoprawna nazwa!");
            }
        }
    }
}
