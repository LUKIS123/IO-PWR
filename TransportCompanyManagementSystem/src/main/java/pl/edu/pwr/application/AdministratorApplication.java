package pl.edu.pwr.application;

import pl.edu.pwr.controllers.DriverController;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.dtos.JobDriverAssignmentDto;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.views.application.AdministratorAppIndex;
import pl.edu.pwr.views.job.JobVerification;

public class AdministratorApplication implements ApplicationInterface {
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
        int choice = AdministratorAppIndex.adminMenu();
        switch (choice) {
            case 1:
                jobController.listJobByStatus(JobStatus.PAID);

            case 2:
                JobDriverAssignmentDto jobDriverAssignmentDto = jobController.acceptForConsideration(JobStatus.PAID);
                int decision = JobVerification.verifyView(jobDriverAssignmentDto.job, jobDriverAssignmentDto.driver);
                if (decision == 0) {
                    jobController.setJobAsVerified(jobDriverAssignmentDto.job.getId());
                } else if (decision == 2) {
                    System.out.println("Wybierz kierowce");
                    Driver newDriver = driverController.listAvailableDrivers();
                    // zapis nowego kierowcy
                    jobController.assignDriverToJob(newDriver, jobDriverAssignmentDto.job);
                    jobController.setJobAsVerified(jobDriverAssignmentDto.job.getId());
                } else {
                    jobController.setJobAsRejected(jobDriverAssignmentDto.job.getId());
                }

            case 3:
                jobController.listAllJobs();

            case 4:
                jobController.listJobByStatus(JobStatus.FINISHED);

            default:
                return;
        }
    }

}
