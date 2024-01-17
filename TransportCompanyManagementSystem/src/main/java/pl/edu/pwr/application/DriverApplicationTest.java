package pl.edu.pwr.application;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.edu.pwr.Repositories.DriverRepository;
import pl.edu.pwr.controllers.DriverController;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.CargoType;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.models.enums.UserType;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static pl.edu.pwr.Repositories.DataStore.jobList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DriverApplicationTest {



    private DriverController driverController;
    private JobController jobController;
    private DriverApplication driverApplication;
    private DriverRepository driverRepository;
    User user;

    @BeforeEach
    void set_up_test_objects() {
        jobController = new JobController();
        driverController = new DriverController();
        user = new User(10,"test_user", UserType.CLIENT);
        driverRepository = new DriverRepository();
        driverApplication = new DriverApplication(user,jobController,driverController);
    }


    @RepeatedTest(5)
    void getAssignedJob() {
        // Arrange
        User user = new User(16,"test_user", UserType.CLIENT);
        Job new_one = new Job(100, 16, 16, CargoType.HEAVY.toString(), JobStatus.PAID.toString(), 300, 1500, true);

        jobList.add(new_one);


        //act
        Job to_check = jobController.listJobInRealization(user.getId());

        //assert
        assertNotNull(to_check);
    }

    @Test
    void acceptJob() {
        // nie ma takiego elementu więc powinien wyrzucić NoSuchElementException exception
        // z metody acceptJob a następnie getById

        // Arrange
        User user = new User(2,"test_user", UserType.CLIENT);

        //assert
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,

                // Act
                () -> driverController.acceptJob(user.getId())
        );
    }

    @Test
    void finishJob() {
    }

    @Test
    void goToWork() {
        //arrange
        User user = new User(3,"test_user", UserType.CLIENT);

        //act
        DriverApplication driverApplication = new DriverApplication(user,jobController,driverController);
        driverApplication.goToWork();

        Driver byId = driverRepository.getById(user.getId());
        //assert
        assertFalse(byId.isDuringRest());
    }


    // przeleci po kolei z tymi parametrami
    @ParameterizedTest
    @CsvSource({
            "3",  // User ID = 3
            "4",  // User ID = 4
    })
    void goToRest(String driverId) {
        driverController.setStatusDuringRest(Integer.parseInt(driverId));
        assertEquals(true,driverRepository.getById(Integer.parseInt(driverId)).isDuringRest());
    }

    @Test
    @AfterAll
    @Disabled
    static void reset(){
        // usunięcie po testach utworzonych obiektów
        jobList.subList(2, 6).clear();
    }

    @Test
    @BeforeAll
    static void check(){
        // sprawdzenie czy lista nie jest pusta
        assertFalse(jobList.isEmpty());
    }
}