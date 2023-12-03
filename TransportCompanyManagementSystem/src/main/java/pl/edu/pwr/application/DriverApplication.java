package pl.edu.pwr.application;

import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.User;

public class DriverApplication {
    private final User user;
    private final JobController jobController;

    public DriverApplication(User user, JobController jobController) {
        this.user = user;
        this.jobController = jobController;
    }

    public void index() {
    }
}
