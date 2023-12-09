package pl.edu.pwr.application;

import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.User;
import pl.edu.pwr.views.application.ClientAppIndex;

import java.sql.SQLException;

public class ClientApplication implements ApplicationInterface {
    private final User user;
    private final JobController jobController;

    public ClientApplication(User user, JobController jobController) {
        this.user = user;
        this.jobController = jobController;
    }

    @Override
    public void index() throws SQLException {
        int choice = ClientAppIndex.clientMenu();
        switch (choice) {
            case 1:
                //jobController.createNewOrder(user.getClientID());

            case 2:
                jobController.makePayment(user);

            case 3:
                jobController.listJobsByOwner(user.getClientID());
                
            default:
                return;
        }
    }

}
