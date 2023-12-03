package pl.edu.pwr.controllers;

import pl.edu.pwr.Repositories.DriverRepository;
import pl.edu.pwr.Repositories.JobRepository;
import pl.edu.pwr.models.User;
import pl.edu.pwr.views.driver.DriverIndex;

public class DriverController {
    private final JobRepository jobRepository;
    private final DriverRepository driverRepository;
    private final User user;

    public DriverController(User user) {
        this.user = user;
        driverRepository = new DriverRepository();
        jobRepository = new JobRepository();
    }

    public void index() {
        int choice = DriverIndex.driverMenu();

    }

    public void acceptJob(int jobId) {

    }

    public void setStatusJobFinished() {

    }

    public void setStatusOnShift() {

    }

    public void setStatusDuringRest() {

    }

    public void setStatusWorking() {

    }
}
