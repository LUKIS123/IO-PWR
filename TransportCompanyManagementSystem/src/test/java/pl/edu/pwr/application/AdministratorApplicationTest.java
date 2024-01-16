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
import pl.edu.pwr.models.enums.UserType;
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
        new Expectations() {{
            user.getId();
            result = 1111;
            minTimes = 0;

            user.getUserType();
            result = UserType.ADMINISTRATOR;
            minTimes = 0;

            jobController.listJobByStatus(JobStatus.PAID);
            result = List.of(
                    new Job(0, "PAID", "HEAVY", 100, 100),
                    new Job(0, "PAID", "LIGHT", 100, 100),
                    new Job(0, "PAID", "FRAGILE", 100, 100)
            );
            minTimes = 0;
        }};

        List<Job> checkedOutJobsToVerify = administratorApplication.checkoutJobsToVerify();

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
        Job jobTest2 = new Job(1, "IN_VERIFICATION_PROCESS", "HEAVY", 100, 100);
        new Expectations() {{
            jobController.acceptForConsideration();
            result = new JobDriverAssignmentDto(driverTest2, jobTest2);
            minTimes = 0;

            new MockUp<JobView>() {
                @Mock
                int verifyView(Job job, Driver driver) {
                    return option;
                }
            };

            new MockUp<JobController>() {
                @Mock
                void setJobAsVerified(int jobId) {
                    jobTest2.setStatus(JobStatus.VERIFIED);
                }
            };

            new MockUp<JobController>() {
                @Mock
                void assignDriverToJob(Driver driver, Job job) {
                    jobTest2.setDriverId(driverTest2.getId());
                }
            };

            new MockUp<JobController>() {
                @Mock
                void setJobAsRejected(int jobId) {
                    jobTest2.setStatus(JobStatus.REJECTED);
                }
            };

            driverController.listAvailableDrivers();
            result = new Driver(0, "Adam");
            minTimes = 0;
        }};

        administratorApplication.verifyJobs();

        if (option == 0) {
            // Akceptacja zlecenia
            assertEquals(JobStatus.VERIFIED, jobTest2.getStatus());
        } else if (option == 1) {
            // Przypisanie kierowcy
            assertEquals(JobStatus.VERIFIED, jobTest2.getStatus());
            assertEquals(driverTest2.getId(), jobTest2.getDriverId());
        } else {
            // Odrzucenie zlecenia
            assertEquals(JobStatus.REJECTED, jobTest2.getStatus());
        }
    }

}
