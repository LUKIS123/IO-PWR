package pl.edu.pwr;

import pl.edu.pwr.Repositories.UserRepository;
import pl.edu.pwr.application.AdministratorApplication;
import pl.edu.pwr.application.ClientApplication;
import pl.edu.pwr.application.DriverApplication;
import pl.edu.pwr.controllers.DriverController;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.User;
import pl.edu.pwr.views.application.ApplicationView;

public class Main {
    public static void main(String[] args) {
        System.setProperty("console.encoding", "UTF-8");

        UserRepository userRepository = new UserRepository();
        String username = ApplicationView.initializeIndex();
        User user = userRepository.getByUsername(username);

        if (user == null) {
            System.out.println("Niepoprawna nazwa!");
            return;
        }

        JobController jobController = new JobController();
        DriverController driverController = new DriverController();

        switch (user.getUserType()) {
            case CLIENT:
                new ClientApplication(user, jobController).index();
                break;
            case ADMINISTRATOR:
                new AdministratorApplication(user, jobController, driverController).index();
                break;
            case DRIVER:
                new DriverApplication(user, jobController, driverController).index();
                break;
            default:
                System.out.println("Niepoprawna nazwa!");
                break;
        }

    }
}

