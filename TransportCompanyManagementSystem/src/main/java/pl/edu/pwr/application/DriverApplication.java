package pl.edu.pwr.application;

import pl.edu.pwr.controllers.DriverController;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.views.application.DriverAppIndex;

import java.sql.SQLException;

public class DriverApplication implements ApplicationInterface {
    private final User user;
    private Job assignedJob;
    private final JobController jobController;
    private final DriverController driverController;

    public DriverApplication(User user, JobController jobController, DriverController driverController) {
        this.user = user;
        this.jobController = jobController;
        this.driverController = driverController;
    }

    @Override
    public void index() throws SQLException {
        int choice = DriverAppIndex.driverMenu();
        switch (choice) {
            case 1:
                assignedJob = jobController.listJobInRealization(user.getClientID());
            case 2:
                if (assignedJob == null) return;
                jobController.acceptJob(assignedJob.getJob_Id());
            case 3:
                if (assignedJob == null) return;
                jobController.setJobAsFinished(assignedJob.getJob_Id());
            case 4:
                driverController.setStatusDuringRest(user.getClientID());
            case 5:
                driverController.setStatusOnShift(user.getClientID());
            default:
                return;
        }

    }
}
