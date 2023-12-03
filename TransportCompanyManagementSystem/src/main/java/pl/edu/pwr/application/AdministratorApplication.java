package pl.edu.pwr.application;

import pl.edu.pwr.controllers.DriverController;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.User;
import pl.edu.pwr.views.application.AdministratorAppIndex;
import pl.edu.pwr.views.job.JobVerification;

public class AdministratorApplication {
    private final User user;
    private final JobController jobController;
    private final DriverController driverController;

    public AdministratorApplication(User user, JobController jobController, DriverController driverController) {
        this.user = user;
        this.jobController = jobController;
        this.driverController = driverController;
    }

    public void index() {
        int choice = AdministratorAppIndex.adminMenu();
    }


    public void startVerificationProcess(int orderId) {
        boolean b = JobVerification.verifyView(null, null);
    }

}
