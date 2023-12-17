package pl.edu.pwr.Repositories;

import pl.edu.pwr.dtos.JobDriverAssignmentDto;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.enums.JobStatus;

import java.util.ArrayList;
import java.util.List;

public class JobRepository extends DataStore implements RepositoryInterface<Job> {
    @Override
    public List<Job> getAll() {
        return jobList;
    }

    @Override
    public Job getById(int id) {
        for (Job job : jobList) {
            if (job.getJobId() == id) {
                return job;
            }
        }
        return null;
    }

    @Override
    public void insert(Job model) {
        jobList.add(model);
        ++JOB_SEQUENCE;
    }

    @Override
    public void delete(int id) {
        Job byId = getById(id);
        jobList.remove(byId);
    }

    public List<Job> getByUserId(int userId) {
        List<Job> byUserId = new ArrayList<>();
        for (Job job : jobList) {
            if (job.getClientId() == userId) {
                byUserId.add(job);
            }
        }
        return byUserId;
    }

    public List<Job> getByStatus(JobStatus status) {
        return jobList
                .stream()
                .filter(job -> job.getStatus() == status)
                .toList();
    }

    public List<JobDriverAssignmentDto> getByStatusWithDriverSuggestion() {
        List<Job> byStatus = getByStatus(JobStatus.PAID);

        List<Driver> availableDrivers = new ArrayList<>();
        for (Driver driver : driverList) {
            if (!driver.isDuringExecutionOfOrder() && !driver.isDuringRest()) {
                availableDrivers.add(driver);
            }
        }

        if (availableDrivers.isEmpty()) {
            availableDrivers = driverList;
        }

        List<JobDriverAssignmentDto> assignmentDtos = new ArrayList<>();
        int size = availableDrivers.size();
        int i = 0;
        for (Job job : byStatus) {
            if (i >= size) {
                i = 0;
            }
            job.setDriverId(availableDrivers.get(i).getId());
            assignmentDtos.add(new JobDriverAssignmentDto(availableDrivers.get(i), job));
            ++i;
        }
        return assignmentDtos;
    }

    public void updateJobDriverAssignment(int driverId, int jobId) {
        Job byId = getById(jobId);
        byId.setDriverId(driverId);
    }

    public Job getAssignedJob(int driverId) {
        return jobList
                .stream()
                .filter(job -> job.getDriverId() == driverId)
                .findFirst()
                .get();
    }
}
