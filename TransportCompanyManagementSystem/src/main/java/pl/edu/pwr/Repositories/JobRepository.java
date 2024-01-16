package pl.edu.pwr.Repositories;

import pl.edu.pwr.dtos.JobDriverAssignmentDto;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.enums.JobStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class JobRepository extends DataStore implements RepositoryInterface<Job> {
    @Override
    public List<Job> getAll() {
        return jobList;
    }

    @Override
    public Job getById(int id) {
        for (Job job : jobList) {
            int jobId = job.getJobId();
            if (jobId == id) {
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
            int clientId = job.getClientId();
            if (clientId == userId) {
                byUserId.add(job);
            }
        }
        return byUserId;
    }

    public List<Job> getByStatus(JobStatus status) {
        List<Job> byStatus = new ArrayList<>();
        for (Job job : jobList) {
            JobStatus jobStatus = job.getStatus();
            if (jobStatus == status) {
                byStatus.add(job);
            }
        }
        return byStatus;
    }

    public List<JobDriverAssignmentDto> getByStatusWithDriverSuggestion() {
        List<Job> byStatus = getByStatus(JobStatus.IN_VERIFICATION_PROCESS);

        List<Driver> availableDrivers = new ArrayList<>();
        for (Driver driver : driverList) {
            boolean duringExecutionOfOrder = driver.isDuringExecutionOfOrder();
            boolean duringRest = driver.isDuringRest();
            if (!duringExecutionOfOrder && !duringRest) {
                availableDrivers.add(driver);
            }
        }

        boolean isEmpty = availableDrivers.isEmpty();
        if (isEmpty == true) {
            availableDrivers = driverList;
        }

        List<JobDriverAssignmentDto> assignmentDtos = new ArrayList<>();
        int size = availableDrivers.size();
        int i = 0;
        for (Job job : byStatus) {
            if (i >= size) {
                i = 0;
            }

            Driver driverToAssign = availableDrivers.get(i);

            int driverToAssignId = driverToAssign.getId();
            job.setDriverId(driverToAssignId);
            assignmentDtos.add(new JobDriverAssignmentDto(driverToAssign, job));
            ++i;
        }
        return assignmentDtos;
    }

    public void updateJobDriverAssignment(int driverId, int jobId) {
        Job byId = getById(jobId);
        byId.setDriverId(driverId);
    }

    public Job getAssignedJob(int driverId) {
        try {
            return jobList
                    .stream()
                    .filter(job -> job.getDriverId() == driverId)
                    .findFirst()
                    .get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
