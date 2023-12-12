package pl.edu.pwr.controllers;

import pl.edu.pwr.Repositories.JobRepository;
import pl.edu.pwr.dtos.CreateJobDto;
import pl.edu.pwr.dtos.JobDriverAssignmentDto;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.JobStatus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobController {
    private final JobRepository jobRepository;

    public JobController() {
        jobRepository = new JobRepository();
    }

    public int listAllJobs() {
        List<Job> all;
        try {
            all = jobRepository.getAll();
        } catch (SQLException e) {
            all = new ArrayList<>();
        }
        int i = Job.jobView.listAll(all);
        return i;
    }

    public Job listJobByStatus(JobStatus status) {
        List<Job> byStatus = new ArrayList<>();
        try {
            byStatus = jobRepository.getByStatus(status);
        } catch (SQLException e) {

        }
        // todo: wszystkim jobom dac status verification process
        int i = Job.jobView.listAll(byStatus);
        return byStatus.stream().filter(x -> x.getJobId() == i).findFirst().get();
    }

    public int listJobsByOwner(int userId) {
        List<Job> byUserId = jobRepository.getByUserId(userId);

        Optional<Integer> index = Job.jobView.listOrders(byUserId);
        if (index.isPresent()) {
            Optional<Job> first = byUserId.stream().filter(x -> x.getJobId() == index.get()).findFirst();
            Job.jobView.displayJobInfo(first.get());
        }
        return 0;
    }

    public JobDriverAssignmentDto acceptForConsideration(JobStatus status) {
        List<JobDriverAssignmentDto> list = new ArrayList<>();
        try {
            list = jobRepository.getByStatusWithDriverSuggestion(status).stream().toList();
        } catch (SQLException e) {

        }

        int i = Job.jobView.listOrdersWithDriverAssigment(list);
        return list.stream().filter(x -> x.job.getJobId() == i).findFirst().get();
    }

    public void assignDriverToJob(Driver driver, Job job) {
        try {
            jobRepository.updateJobDriverAssignment(driver.getId(), job.getJobId());
            Job.jobView.displayDriverJobInfo(driver, job);
        } catch (SQLException e) {

        }
    }

    public void setJobAsPaid(int jobId) {
        try {
            Job byId = jobRepository.getById(jobId);
            byId.setStatus(JobStatus.PAID);
            jobRepository.update(byId.getJobId(), byId);
            Job.jobView.displayJobInfo(byId);
        } catch (SQLException e) {

        }
    }

    public void setJobAsCancelled(int jobId) {
        try {
            Job byId = jobRepository.getById(jobId);
            byId.setStatus(JobStatus.CANCELLED);
            jobRepository.update(jobId, byId);
            Job.jobView.displayJobInfo(byId);
        } catch (SQLException e) {
        }
    }

    public void setJobAsVerified(int jobId) {
        try {
            Job byId = jobRepository.getById(jobId);
            byId.setStatus(JobStatus.VERIFIED);
            jobRepository.update(jobId, byId);
            Job.jobView.displayJobInfo(byId);
        } catch (SQLException e) {
        }
    }

    public void setJobAsRejected(int jobId) {
        try {
            Job byId = jobRepository.getById(jobId);
            byId.setStatus(JobStatus.REJECTED);
            jobRepository.update(jobId, byId);
            Job.jobView.displayJobInfo(byId);
        } catch (SQLException e) {

        }
    }

    public void createNewOrder(int clientId) {
        try {
            CreateJobDto dto = Job.jobView.order();
            Job job = new Job(clientId, "NEWLY_ADDED", dto.cargoType.toString(), dto.distance, dto.weight);
            jobRepository.insert(job);
            Job.jobView.displayJobInfo(job);
        } catch (SQLException e) {

        }
    }

    public void makePayment(User user) {
        List<Job> byUserId = jobRepository.getByUserId(user.getId());
        int choice = Job.jobView.listAll(byUserId);
        Job job = byUserId.stream().filter(x -> x.getJobId() == choice).findFirst().get();

        boolean paymentConfirmed = Job.jobView.tryMakePayment(job);
        if (paymentConfirmed == true) {
            setJobAsPaid(job.getJobId());
        } else {
            setJobAsCancelled(job.getJobId());
        }
    }

    public void setJobAsFinished(int jobId) {
        try {
            Job byId = jobRepository.getById(jobId);
            Job.jobView.displayJobInfo(byId);
        } catch (SQLException e) {

        }
    }

    public void acceptJob(int jobId) {
        try {
            Job byId = jobRepository.getById(jobId);
            Job.jobView.displayJobInfo(byId);
        } catch (SQLException e) {

        }
    }

    public Job listJobInRealization(int driverId) {
        Job assignedJob = jobRepository.getAssignedJob(driverId);
        Job.jobView.displayJobInfo(assignedJob);
        return assignedJob;
    }

}
