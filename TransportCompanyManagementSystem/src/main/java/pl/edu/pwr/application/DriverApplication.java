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
                getAssignedJob();
            case 2:
                acceptJob();
            case 3:
                finishJob();
            case 4:
                goToRest();
            case 5:
                goToWork();
            default:
                return;
        }
    }

    Job getAssignedJob() {
        assignedJob = jobController.listJobInRealization(user.getId());
        return assignedJob;
    }

    Job acceptJob() {
        if (assignedJob == null) return null;
        int assignedJobId = assignedJob.getJobId();
        jobController.acceptJob(assignedJobId);
        driverController.acceptJob(user.getId());
        return jobController.listJobInRealization(assignedJobId);
    }

    Job finishJob() {
        if (assignedJob == null) return null;
        int assignedJobId = assignedJob.getJobId();
        jobController.setJobAsFinished(assignedJobId);
        driverController.finishJob(user.getId());
        return jobController.listJobInRealization(assignedJobId);
    }

    void goToRest() {
        driverController.setStatusDuringRest(user.getId());
    }

    void goToWork() {
        driverController.setStatusOnShift(user.getId());
    }

}
