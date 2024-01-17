package pl.edu.pwr.application;

import mockit.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.edu.pwr.Repositories.JobRepository;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.dtos.CreateJobDto;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.CargoType;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.views.job.JobView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientApplicationTest {
    List<Job> mockJobsList;
    @Mocked
    User user;
    @Mocked
    JobRepository jobRepository;
    @Tested
    JobController jobController;

    @BeforeEach
    void setUp() {
        mockJobsList = new ArrayList<>(Arrays.asList(
                new Job(1, 0, 111, "HEAVY", "NEWLY_ADDED", 100, 100, false),
                new Job(2, 0, 111, "HEAVY", "NEWLY_ADDED", 200, 200, false)
        ));
        new Expectations() {{
            user.getId();
            result = 111;
            minTimes = 0;

            jobRepository.getAll();
            result = mockJobsList;
            minTimes = 0;
        }};
    }

    @Test
    void createNewJob() {
        // Arrange
        int index = mockJobsList.size() - 1;
        new Expectations() {{
            new MockUp<JobView>() {
                @Mock
                CreateJobDto order() {
                    return new CreateJobDto(
                            CargoType.HAZARDOUS,
                            200,
                            50);
                }
            };

            jobRepository.insert((Job) any);
            result = new Delegate() {
                void insert(Job job) {
                    mockJobsList.add(job);
                }
            };
            minTimes = 1;

        }};

        // Act
        jobController.createNewOrder(user.getId());

        // Assert
        assertEquals(user.getId(), mockJobsList.get(index + 1).getClientId());
        assertEquals(JobStatus.NEWLY_ADDED, mockJobsList.get(index + 1).getStatus());
        assertEquals(CargoType.HAZARDOUS, mockJobsList.get(index + 1).getCargoType());
        assertEquals(200, mockJobsList.get(index + 1).getDistance());
        assertEquals(50, mockJobsList.get(index + 1).getWeight());

        new Verifications() {{
            jobRepository.insert((Job) any);
            times = 1;
        }};
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void makePayment(boolean paymentAccepted) {
        // Arrange
        new Expectations() {{
            jobRepository.getByUserId(anyInt);
            result = new Delegate() {
                List<Job> getByUserId(int id) {
                    return mockJobsList;
                }
            };
            minTimes = 1;
            new MockUp<JobView>() {
                @Mock
                int listAll(List<Job> jobs) {
                    return mockJobsList.get(0).getJobId();
                }
            };
            new MockUp<JobView>() {
                @Mock
                boolean tryMakePayment(Job job) {
                    return paymentAccepted;
                }
            };
            jobRepository.getById(anyInt);
            result = new Delegate() {
                Job getById(int jobId) {
                    return mockJobsList.get(0);
                }
            };
            minTimes = 1;
        }};

        // Act
        jobController.makePayment(user);

        // Assert
        if (paymentAccepted) {
            assertEquals(JobStatus.PAID, mockJobsList.get(0).getStatus());
            assertTrue(mockJobsList.get(0).isPaid());
        } else {
            assertEquals(JobStatus.CANCELLED, mockJobsList.get(0).getStatus());
            assertFalse(mockJobsList.get(0).isPaid());
        }
        assertEquals(user.getId(), mockJobsList.get(0).getClientId());
        assertEquals(100, mockJobsList.get(0).getDistance());
        assertEquals(100, mockJobsList.get(0).getWeight());

        new Verifications() {{
            jobRepository.getByUserId(anyInt);
            times = 1;

            jobRepository.getById(anyInt);
            times = 1;
        }};
    }

}