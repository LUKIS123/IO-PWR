package pl.edu.pwr.application;

import pl.edu.pwr.controllers.DriverController;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.dtos.JobDriverAssignmentDto;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.views.application.ApplicationView;

public class AdministratorApplication implements ApplicationInterface {
    private final ApplicationView applicationView = new ApplicationView();
    private final User user;
    private final JobController jobController;
    private final DriverController driverController;

    public AdministratorApplication(User user, JobController jobController, DriverController driverController) {
        this.user = user;
        this.jobController = jobController;
        this.driverController = driverController;
    }

    @Override
    public void index() {
        int choice = -1;
        while (choice != 0) {
            choice = applicationView.adminMenu();
            switch (choice) {
                case 1:
                    jobController.listJobByStatus(JobStatus.PAID);
                    break;
                case 2:
                    verifyJobs();
                    break;
                case 3:
                    jobController.listAllJobs();
                    break;
                case 4:
                    jobController.listJobByStatus(JobStatus.FINISHED);
                    break;
                default:
                    break;
            }
        }
    }

    private void verifyJobs() {
        JobDriverAssignmentDto jobDriverAssignmentDto = jobController.acceptForConsideration();
        int decision = Job.jobView.verifyView(jobDriverAssignmentDto.job, jobDriverAssignmentDto.driver);
        if (decision == 0) {
            jobController.setJobAsVerified(jobDriverAssignmentDto.job.getJobId());
        } else if (decision == 1) {
            System.out.println("Wybierz kierowce");
            Driver newDriver = driverController.listAvailableDrivers();
            // zapis nowego kierowcy
            jobController.assignDriverToJob(newDriver, jobDriverAssignmentDto.job);
            jobController.setJobAsVerified(jobDriverAssignmentDto.job.getJobId());
        } else {
            jobController.setJobAsRejected(jobDriverAssignmentDto.job.getJobId());
        }
    }

}
