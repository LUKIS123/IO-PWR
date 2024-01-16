package pl.edu.pwr.application;

import mockit.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.edu.pwr.Repositories.JobHistoryRepository;
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
    @Mocked
    User user;
    @Injectable
    JobRepository jobRepository;
    @Tested
    JobController jobController;

    List<Job> mockJobsList;

    @BeforeEach
    void setUp() {
        new Expectations() {{
            user.getId();
            result = 1111;
            minTimes = 1;

            jobRepository.getAll();
            result = mockJobsList;
            minTimes = 0;
        }};

        mockJobsList = new ArrayList<>(Arrays.asList(
                new Job(1, 0, user.getId(), "HEAVY", "NEWLY_ADDED", 100, 100, false),
                new Job(2, 0, user.getId(), "HEAVY", "NEWLY_ADDED", 200, 200, false)
        ));
    }

    @Test
    void createNewJob() {
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
            new MockUp<JobRepository>() {
                @Mock
                void insert(Job job) {
                    mockJobsList.add(job);
                }
            };
        }};

        jobController.createNewOrder(user.getId());


        assertEquals(JobStatus.NEWLY_ADDED, mockJobsList.get(index + 1).getStatus());
        assertEquals(CargoType.HAZARDOUS, mockJobsList.get(index + 1).getCargoType());
        assertEquals(200, mockJobsList.get(index + 1).getDistance());
        assertEquals(50, mockJobsList.get(index + 1).getWeight());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void makePayment(boolean paymentAccepted) {
        new Expectations() {{
            new MockUp<JobRepository>() {
                @Mock
                List<Job> getByUserId(int id) {
                    return mockJobsList;
                }
            };
            new MockUp<JobView>() {
                @Mock
                int listAll(List<Job> jobs) {
                    return mockJobsList.get(0).getJobId();
                }
            };
            new MockUp<JobView>() {
                @Mock
                boolean tryMakePayment(Job jobs) {
                    return paymentAccepted;
                }
            };
            new MockUp<JobRepository>() {
                @Mock
                Job getById(int jobId) {
                    return mockJobsList.get(0);
                }
            };
        }};

        jobController.makePayment(user);


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
    }

}