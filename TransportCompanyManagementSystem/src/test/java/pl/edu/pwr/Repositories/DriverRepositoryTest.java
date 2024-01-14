package pl.edu.pwr.Repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.edu.pwr.models.Driver;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DriverRepositoryTest implements TestExecutionExceptionHandler {
    private static DriverRepository repository;
    private static String testDriverName;
    private static int testDriverId;
    private static boolean testDriverDuringExecutionOfOrder;
    private static boolean testDriverDuringRest;

    // Wykonuje sie przed wykonaniem testow
    @BeforeAll
    static void initializeRepo() {
        repository = new DriverRepository();
        repository.getAll().clear();

        Driver driver1 = new Driver(DataStore.USER_SEQUENCE++, "Jan", false, false);
        Driver driver2 = new Driver(DataStore.USER_SEQUENCE++, "Aleks", false, false);
        Driver driver3 = new Driver(DataStore.USER_SEQUENCE++, "Tomek", false, false);
        Driver driver4 = new Driver(DataStore.USER_SEQUENCE++, "Roman", false, false);
        Driver driver5 = new Driver(DataStore.USER_SEQUENCE++, "Piotr", false, false);
        repository.getAll().add(driver1);
        repository.getAll().add(driver2);
        repository.getAll().add(driver3);
        repository.getAll().add(driver4);
        repository.getAll().add(driver5);
    }

    // Wykonuje sie przed kazdym poszczegolnym testem
    @BeforeEach
    void addTestDriver() {
        testDriverId = DataStore.USER_SEQUENCE++;
        testDriverName = "test_" + testDriverId + "_driver_name";

        testDriverDuringExecutionOfOrder = true;
        testDriverDuringRest = true;

        Driver testDriver = new Driver(testDriverId, testDriverName, testDriverDuringExecutionOfOrder, testDriverDuringRest);
        repository.getAll().add(testDriver);
    }

    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        if (throwable instanceof NoSuchElementException) {
            return;
        } else throw throwable;
    }

    @Test
    @ExtendWith(DriverRepositoryTest.class)
    void getById() {
        // Act

        // Nieistniejący kierowca
        Driver driverNonExisting = repository.getById(Integer.MAX_VALUE);

        // Istniejący kierowca
        Driver found = repository.getById(testDriverId);

        // Assert
        assertNull(driverNonExisting);
        assertThrows(NoSuchElementException.class, () -> repository.getById(Integer.MAX_VALUE));

        assertEquals(testDriverId, found.getId());
        assertEquals(testDriverName, found.getUsername());
        assertEquals(testDriverDuringExecutionOfOrder, found.isDuringExecutionOfOrder());
        assertEquals(testDriverDuringRest, found.isDuringRest());
    }

    @Test
    void insert() {
        // Arrange
        int id = DataStore.USER_SEQUENCE++;
        Driver driverToInsert = new Driver(id, "Adam", true, true);
        repository.insert(driverToInsert);

        // Act
        Optional<Driver> first = repository.getAll()
                .stream()
                .filter(d -> d.getId() == id)
                .findFirst();

        Driver driverFound = null;
        if (first.isPresent()) {
            driverFound = first.get();
        }

        // Assert
        assertNotNull(driverFound);
        assertEquals(driverToInsert.getId(), driverFound.getId());
        assertEquals(driverToInsert.getUsername(), driverFound.getUsername());
        assertEquals(driverToInsert.isDuringExecutionOfOrder(), driverFound.isDuringExecutionOfOrder());
        assertEquals(driverToInsert.isDuringRest(), driverFound.isDuringRest());
    }

    @ExtendWith(DriverRepositoryTest.class)
    @ParameterizedTest
    @ValueSource(ints = {10, -15, Integer.MAX_VALUE})
    void delete(int id) {
        // Act
        repository.delete(id);

        Driver byId = repository.getById(id);
        boolean isDriverFound;
        if (byId == null) {
            isDriverFound = false;
        } else {
            isDriverFound = true;
        }

        // Assert
        assertThrows(NoSuchElementException.class, () -> repository.delete(Integer.MAX_VALUE));

        assertFalse(isDriverFound);
        assertNull(byId);
    }

    @Test
    void getAvailableDrivers() {
        // isDuringExecution = false
        // isDuringRest = false
    }

}
