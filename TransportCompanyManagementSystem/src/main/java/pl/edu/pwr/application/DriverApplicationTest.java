package pl.edu.pwr.application;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import pl.edu.pwr.Repositories.DriverRepository;
import pl.edu.pwr.controllers.DriverController;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.CargoType;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.models.enums.UserType;
import pl.edu.pwr.views.application.ApplicationView;

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

import pl.edu.pwr.models.enums.UserType;


class DriverApplicationTest {

    static JobController jobController = new JobController();
    static DriverController driverController = new DriverController();
    User user = new User(10,"test_user", UserType.CLIENT);
    static DriverRepository driverRepository = new DriverRepository();


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
        driverController.setStatusOnShift(user.getId());
        Driver byId = driverRepository.getById(user.getId());

        //assert
        assertFalse(byId.isDuringRest());
    }

    @Test
    @Disabled
    void goToRest() {
        DriverApplication d = new DriverApplication(user,jobController,driverController);
        d.goToRest();
    }

    @Test
    @AfterAll
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