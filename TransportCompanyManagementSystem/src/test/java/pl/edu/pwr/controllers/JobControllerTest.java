package pl.edu.pwr.controllers;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.edu.pwr.Repositories.JobRepository;
import pl.edu.pwr.dtos.CreateJobDto;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.enums.CargoType;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.views.job.JobView;

import static org.junit.jupiter.api.Assertions.*;

class JobControllerTest {


    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class SetJobStatusTest{
        private static JobRepository repository;
        private static Job testJob;
        @BeforeAll
        static void initializeJob(){
            repository = new JobRepository();
            testJob = new Job(1, 1, 1, "HEAVY", "NEWLY_ADDED", 100, 100, false);
            repository.getAll().add(testJob);
        }

        @Test
        @Order(1)
        void setJobAsPaid() {
            // Act
            testJob.setStatus(JobStatus.PAID);
            // Assert
            assertEquals(JobStatus.PAID, testJob.getStatus());
        }

        @Test
        @Order(2)
        void setJobAsCancelled() {
            // Act
            testJob.setStatus(JobStatus.CANCELLED);
            // Assert
            assertEquals(JobStatus.CANCELLED, testJob.getStatus());
        }

        @Test
        @Order(3)
        void setJobAsVerified() {
            // Act
            testJob.setStatus(JobStatus.VERIFIED);
            // Assert
            assertEquals(JobStatus.VERIFIED, testJob.getStatus());
        }

        @Test
        @Order(4)
        void setJobAsRejected() {
            // Act
            testJob.setStatus(JobStatus.REJECTED);
            // Assert
            assertEquals(JobStatus.REJECTED, testJob.getStatus());
        }
    }

    private final JobRepository repository = new JobRepository();
    private final JobController testController = new JobController();

    @ParameterizedTest
    @CsvSource({
            "HEAVY, 100, 150",
            "FRAGILE, 200, 75",
            "HAZARDOUS, 80, 90"
    })
    @Tag("create-new-order")
    void createNewOrder(String cargoType, int distance, int weight) {
        // Arrange
        CreateJobDto dto = new CreateJobDto();
        dto.setCargoType(CargoType.valueOf(cargoType));
        dto.setDistance(distance);
        dto.setWeight(weight);

        // Act
        testController.createNewOrder(1, dto);

        // Assert
        Job testJob = repository.getAll().get(repository.getAll().size() - 1);
        assertEquals(1, testJob.getClientId());
        assertEquals(JobStatus.NEWLY_ADDED, testJob.getStatus());
        assertEquals(CargoType.valueOf(cargoType), testJob.getCargoType());
        assertEquals(distance, testJob.getDistance());
        assertEquals(weight, testJob.getWeight());
    }

    @ParameterizedTest
    @CsvSource({
            "1, IN_PROGRESS, HEAVY, 100, 100",
            "2, NEWLY_ADDED, FRAGILE, 150, 75",
            "3, CANCELLED, REGULAR, 200, 300"
    })
    @Tag("job-in-realization")
    void listJobInRealization(int driverId, String jobStatus, String cargoType, int distance, int weight) {
        // Arrange
        Job testJob = new Job(1, 1, 1, cargoType, jobStatus, distance, weight, false);
        repository.getAll().add(testJob);

        // Act
        Job result = testController.listJobInRealization(driverId);

        // Assert
        if (result != null && JobStatus.IN_PROGRESS.equals(result.getStatus())) {
            assertEquals(JobStatus.IN_PROGRESS, result.getStatus());
        } else {
            assertNull(result);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "HEAVY, 100, 100",
            "HAZARDOUS, 150, 200",
            "FRAGILE, 80, 50"
    })
    @Tag("calculate-cost")
    void calculateCost(String cargoType, int distance, int weight) {
        // Arrange
        Job testJob = new Job(1, 1, 1, cargoType, "NEWLY_ADDED", distance, weight, false);

        // Act
        int cargoTypeAddedCost = 0;
        switch (cargoType) {
            case "HEAVY":
                cargoTypeAddedCost = 500;
                break;
            case "HAZARDOUS":
                cargoTypeAddedCost = 1000;
                break;
            case "FRAGILE":
                cargoTypeAddedCost = 700;
                break;
            default:
                cargoTypeAddedCost = 0;
                break;
        }
        int expectedCost = (int) (1.2 * distance + cargoTypeAddedCost + 0.8 * weight);

        testController.calculateCost(testJob);

        // Assert
        assertEquals(expectedCost, testJob.getCost());
    }
}