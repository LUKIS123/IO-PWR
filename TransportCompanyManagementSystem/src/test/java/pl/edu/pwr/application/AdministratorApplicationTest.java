package pl.edu.pwr.application;

import mockit.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.edu.pwr.controllers.DriverController;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.dtos.JobDriverAssignmentDto;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.views.job.JobView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdministratorApplicationTest {

    @Injectable
    JobController jobController;

    @Injectable
    DriverController driverController;

    @Injectable
    User user;

    @Tested
    AdministratorApplication administratorApplication;

    @Test
    void checkoutJobsToVerify() {
        // Arrange
        new Expectations() {{
            user.getId();
            result = 1111;
            minTimes = 1;

            jobController.listJobByStatus(JobStatus.PAID);
            result = List.of(
                    new Job(0, "PAID", "HEAVY", 100, 100),
                    new Job(0, "PAID", "LIGHT", 100, 100),
                    new Job(0, "PAID", "FRAGILE", 100, 100)
            );
            minTimes = 0;
        }};

        // Act
        List<Job> checkedOutJobsToVerify = administratorApplication.checkoutJobsToVerify();

        // Assert
        assertEquals(administratorApplication.getUserId(), user.getId());

        assertEquals(checkedOutJobsToVerify.get(0).getStatus(), JobStatus.IN_VERIFICATION_PROCESS);
        assertEquals(checkedOutJobsToVerify.get(1).getStatus(), JobStatus.IN_VERIFICATION_PROCESS);
        assertEquals(checkedOutJobsToVerify.get(2).getStatus(), JobStatus.IN_VERIFICATION_PROCESS);

        new Verifications() {{
            administratorApplication.checkoutJobsToVerify();
            times = 1;
        }};
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void verifyJobs(int option) {
        Driver driverTest2 = new Driver(1, "Antoni");
        Job jobTest2 = new Job(1, 0, 1, "HEAVY", "IN_VERIFICATION_PROCESS", 100, 100, true);
        new Expectations() {{
            jobController.acceptForConsideration();
            result = new JobDriverAssignmentDto(driverTest2, jobTest2);
            minTimes = 1;

            new MockUp<JobView>() {
                @Mock
                int verifyView(Job job, Driver driver) {
                    return option;
                }
            };

            jobController.setJobAsVerified(anyInt);
            result = new Delegate() {
                void setJobAsVerified(int jobId) {
                    if (jobId == jobTest2.getJobId()) {
                        jobTest2.setStatus(JobStatus.VERIFIED);
                    }
                }
            };
            minTimes = 0;

            jobController.assignDriverToJob((Driver) any, (Job) any);
            result = new Delegate() {
                void assignDriverToJob(Driver driver, Job job) {
                    jobTest2.setDriverId(driverTest2.getId());
                }
            };
            minTimes = 0;

            jobController.setJobAsRejected(anyInt);
            result = new Delegate() {
                void setJobAsRejected(int jobId) {
                    if (jobId == jobTest2.getJobId()) {
                        jobTest2.setStatus(JobStatus.REJECTED);
                    }
                }
            };
            minTimes = 0;

            driverController.listAvailableDrivers();
            result = new Driver(0, "Adam");
            minTimes = 0;
        }};

        administratorApplication.verifyJobs();

        if (option == 0) {
            // Akceptacja zlecenia
            assertEquals(0, jobTest2.getDriverId());
            assertEquals(JobStatus.VERIFIED, jobTest2.getStatus());
        } else if (option == 1) {
            // Przypisanie kierowcy
            assertEquals(JobStatus.VERIFIED, jobTest2.getStatus());
            assertEquals(driverTest2.getId(), jobTest2.getDriverId());
        } else {
            // Odrzucenie zlecenia
            assertEquals(0, jobTest2.getDriverId());
            assertEquals(JobStatus.REJECTED, jobTest2.getStatus());
        }
    }

}
