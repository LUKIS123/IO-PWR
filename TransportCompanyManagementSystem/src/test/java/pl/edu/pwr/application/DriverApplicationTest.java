package pl.edu.pwr.application;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import pl.edu.pwr.Repositories.DriverRepository;
import pl.edu.pwr.controllers.DriverController;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.CargoType;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.models.enums.UserType;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ExceptionHandlerExtension.class)
class DriverApplicationTest {
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class DriverActionsTest {
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
            System.out.println("NESTED TEST NR 1");
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
            System.out.println("NESTED TEST NR 2");
            // Act
            driverApplication.acceptJob();

            // Assert
            Job assignedJob = driverApplication.getAssignedJob();

            assertEquals(JobStatus.IN_PROGRESS, assignedJob.getStatus());
        }

        @Test
        @Order(3)
        void goToWork() {
            System.out.println("NESTED TEST NR 3");
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
            System.out.println("NESTED TEST NR 4");
            // Act
            driverApplication.finishJob();

            // Assert
            Job assignedJob = driverApplication.getAssignedJob();

            assertEquals(JobStatus.FINISHED, assignedJob.getStatus());
        }

        @Test
        @Order(5)
        void goToRest() {
            System.out.println("NESTED TEST NR 5");
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


    // Pozostałe testy
//    static DriverController driverController;
//    static JobController jobController;
//    static DriverApplication driverApplication;
//    static DriverRepository driverRepository;
//    static User user;
//
//    @BeforeAll
//    static void set_up_test_objects() {
//        jobController = new JobController();
//        driverRepository = new DriverRepository();
//        driverController = new DriverController(driverRepository);
//        user = new User(10, "test_user", UserType.DRIVER);
//        driverApplication = new DriverApplication(user, jobController, driverController);
//    }
//
//    @Order(1)
//    @RepeatedTest(5)
//    void getAssignedJob() {
//        // Arrange
//        User user = new User(16, "test_user", UserType.DRIVER);
//        Job new_one = new Job(100, 16, 16, CargoType.HEAVY.toString(), JobStatus.PAID.toString(), 300, 1500, true);
//        jobController.getJobRepository().getAll().add(new_one);
//
//        //act
//        Job to_check = jobController.listJobInRealization(user.getId());
//
//        //assert
//        assertNotNull(to_check);
//    }
//
//    @Order(2)
//    @Test
//    void acceptJob() {
//        // nie ma takiego elementu więc powinien wyrzucić NoSuchElementException exception
//        // z metody getById
//
//        // Arrange
//        User userNonExisting = new User(driverRepository.getAll().size() + 1000, "test_user", UserType.DRIVER);
//
//        //assert
//        NoSuchElementException exception = assertThrows(
//                NoSuchElementException.class,
//                // Act
//                () -> driverRepository.getById(userNonExisting.getId())
//        );
//
//        assertFalse(driverController.acceptJob(userNonExisting.getId()));
//    }
//
//    @Test
//    void goToWork() {
//        //arrange
//        User user = new User(3, "test_user", UserType.DRIVER);
//
//        //act
//        DriverApplication driverApplication = new DriverApplication(user, jobController, driverController);
//        driverApplication.goToWork();
//
//        Driver byId = driverRepository.getById(user.getId());
//        //assert
//        assertFalse(byId.isDuringRest());
//    }
//
//    // przeleci po kolei z tymi parametrami z CSV
//    @ParameterizedTest
//    @CsvSource({  // id driver do testu
//            "3",
//            "4",
//    })
//    void goToRest(String ID) {
//        User u = new User(Integer.parseInt(ID), "AAAA", UserType.DRIVER);
//        DriverApplication d = new DriverApplication(u, jobController, driverController);
//        d.goToRest();
//        assertTrue(driverRepository.getById(Integer.parseInt(ID)).isDuringRest());
//    }
//
//    static Stream<Integer> driverIdstream() {
//        return Stream.of(3, 4);
//    }
//
//    @ParameterizedTest
//    @MethodSource("driverIdstream")
//    void goToRest2(int userId) {
//        User u = new User(userId, "AAAA", UserType.DRIVER);
//        DriverApplication d = new DriverApplication(u, jobController, driverController);
//        d.goToRest();
//
//        assertTrue(driverRepository.getById(userId).isDuringRest(), "Driver status should be set to true after going to rest.");
//    }
//
//    @AfterAll
//    @Disabled   // nie używane
//    static void reset() {
//        // usunięcie po testach utworzonych obiektów
//        driverRepository.getAll().subList(0, driverRepository.getAll().size() - 1).clear();
//    }

}
