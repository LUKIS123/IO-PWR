package pl.edu.pwr.application;

import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.User;
import pl.edu.pwr.views.application.ClientAppIndex;

public class ClientApplication {
    private final User user;
    private final JobController jobController;

    public ClientApplication(User user, JobController jobController) {
        this.user = user;
        this.jobController = jobController;
    }

    public void index() {
        int choice = ClientAppIndex.clientMenu();
    }

}
