package pl.edu.pwr.application;

import org.junit.jupiter.api.*;
import pl.edu.pwr.controllers.DriverController;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.CargoType;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.models.enums.UserType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DriverApplicationTest {
    static User user = new User(111, "test_driver_user", UserType.DRIVER);
    static JobController jobController = new JobController();
    static DriverController driverController = new DriverController();
    static DriverApplication driverApplication = new DriverApplication(user, jobController, driverController);

    @BeforeAll
    static void setup() {
        List<Job> all = jobController.getJobRepository().getAll();
        assertFalse(all.isEmpty());

        Driver driver = new Driver(user.getId(), user.getUsername(), false, false);
        driverController.getDriverRepository().insert(driver);

        Job job = new Job(222, user.getId(), 333, CargoType.HEAVY.toString(), JobStatus.VERIFIED.toString(), 100, 100, true);
        jobController.getJobRepository().insert(job);
    }

    @Test
    @Order(1)
    void getAssignedJob() {
        // Act
        Job assignedJob = driverApplication.getAssignedJob();

        // Assert
        assertNotNull(assignedJob);
        assertEquals(JobStatus.VERIFIED, assignedJob.getStatus());
        assertEquals(user.getId(), assignedJob.getDriverId());
        assertEquals(333, assignedJob.getClientId());
        assertEquals(CargoType.HEAVY, assignedJob.getCargoType());
        assertEquals(100, assignedJob.getWeight());
        assertTrue(assignedJob.isPaid());
    }

    @Test
    @Order(2)
    void acceptJob() {
        // Act
        driverApplication.acceptJob();

        // Assert
        Job assignedJob = driverApplication.getAssignedJob();

        assertEquals(JobStatus.IN_PROGRESS, assignedJob.getStatus());
    }

    @Test
    @Order(3)
    void goToWork() {
        // Act
        driverApplication.goToWork();

        // Assert
        Driver byId = driverController.getDriverRepository().getById(user.getId());

        assertFalse(byId.isDuringRest());
        assertTrue(byId.isDuringExecutionOfOrder());
    }

    @Test
    @Order(4)
    void finishJob() {
        // Act
        driverApplication.finishJob();

        // Assert
        Job assignedJob = driverApplication.getAssignedJob();

        assertEquals(JobStatus.FINISHED, assignedJob.getStatus());
    }

    @Test
    @Order(5)
    void goToRest() {
        // Act
        driverApplication.goToRest();

        // Assert
        Driver byId = driverController.getDriverRepository().getById(user.getId());

        assertNotNull(byId);
        assertEquals(user.getId(), byId.getId());
        assertTrue(byId.isDuringRest());
        assertFalse(byId.isDuringExecutionOfOrder());
    }

    @AfterAll
    static void reset() {
        jobController.getJobRepository().getAll().remove(jobController.getJobRepository().getAll().size() - 1);
        driverController.getDriverRepository().getAll().remove(driverController.getDriverRepository().getAll().size() - 1);
    }

}
