package pl.edu.pwr.application;

import pl.edu.pwr.controllers.DriverController;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.views.application.ApplicationView;

public class DriverApplication implements ApplicationInterface {
    private final ApplicationView applicationView = new ApplicationView();
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
    public void index() {
        int choice = applicationView.driverMenu();
        switch (choice) {
            case 1:
                assignedJob = jobController.listJobInRealization(user.getId());
            case 2:
                if (assignedJob == null) return;
                jobController.acceptJob(assignedJob.getJobId());
                driverController.acceptJob(user.getId());
            case 3:
                if (assignedJob == null) return;
                jobController.setJobAsFinished(assignedJob.getJobId());
                driverController.finishJob(user.getId());
            case 4:
                driverController.setStatusDuringRest(user.getId());
            case 5:
                driverController.setStatusOnShift(user.getId());
            default:
                return;
        }
    }

}
