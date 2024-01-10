package pl.edu.pwr.Repositories;

import org.junit.jupiter.api.Test;
import pl.edu.pwr.models.Driver;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DriverRepositoryTest {
    @Test
    void getById() {
        // Arrange
        DriverRepository repository = new DriverRepository();
        Driver driverToFind = new Driver(DataStore.USER_SEQUENCE, "driver_name", false, false);
        repository.getAll().add(driverToFind);

        // Act
        Driver found = repository.getById(driverToFind.getId());

        // Assert
        assertEquals(driverToFind.getId(), found.getId());
        assertEquals("driver_name", found.getUsername());
        assertFalse(found.isDuringExecutionOfOrder());
        assertFalse(found.isDuringRest());
    }

    @Test
    void insert() {
        // Arrange
        DriverRepository driverRepository = new DriverRepository();
        Driver driverToInsert = new Driver(DataStore.USER_SEQUENCE, "Adam");
        driverRepository.insert(driverToInsert);

        // Act
        Optional<Driver> first = driverRepository.getAll()
                .stream()
                .filter(d -> d.getId() == driverToInsert.getId())
                .findFirst();

        Driver d = null;
        if (first.isPresent()) {
            d = first.get();
        }

        // Assert
        assertNotNull(d);
        assertEquals(d.getId(), driverToInsert.getId());
    }

    @Test
    void delete() {
        // Arrange
        DriverRepository repository = new DriverRepository();
        Driver driverToDelete = new Driver(DataStore.USER_SEQUENCE, "driver_name", false, false);
        repository.getAll().add(driverToDelete);
        repository.delete(driverToDelete.getId());

        // Act
        Driver byId = repository.getById(1);
        boolean isDriverFound;
        if (byId == null) {
            isDriverFound = false;
        } else {
            isDriverFound = true;
        }

        // Assert
        assertFalse(isDriverFound);
    }

    @Test
    void getAvailableDrivers() {
    }
}