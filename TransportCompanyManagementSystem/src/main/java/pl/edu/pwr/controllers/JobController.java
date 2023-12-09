package pl.edu.pwr.controllers;

import pl.edu.pwr.Repositories.JobRepository;
import pl.edu.pwr.dtos.CreateJobDto;
import pl.edu.pwr.dtos.JobDriverAssignmentDto;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.views.job.JobAssignmentInfo;
import pl.edu.pwr.views.job.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JobController {
    private final JobRepository jobRepository;

    public JobController() {
        jobRepository = new JobRepository();
    }

    public int listAllJobs() throws SQLException {
        List<Job> all = jobRepository.getAll();
        int i = ListJobs.listAll(all);
        return i;
    }

    public Job listJobByStatus(JobStatus status) {
        List<JobDriverAssignmentDto> byStatus = jobRepository.getByStatusWithDriverSuggestion(status);
        // todo: wszystkim jobom dac status verification process
        int i = CheckoutNewJobs.listOrders(byStatus);
        return byStatus.stream().filter(x -> x.driver.getClientID() == i).findFirst().get().job;
    }

    public int listJobsByOwner(int userId) {
        List<Job> byUserId = jobRepository.getByUserId(userId);

        Optional<Integer> index = ClientJobsIndex.listOrders(byUserId);
        if (index.isPresent()) {
            Optional<Job> first = byUserId.stream().filter(x -> x.getJob_Id() == index.get()).findFirst();
            JobInfo.displayJobInfo(first.get());
        }
        return 0;
    }

    public JobDriverAssignmentDto acceptForConsideration(JobStatus status) {
        List<JobDriverAssignmentDto> list = jobRepository.getByStatusWithDriverSuggestion(status).stream().toList();
        int i = CheckoutNewJobs.listOrders(list);
        return list.stream().filter(x -> x.job.getJob_Id() == i).findFirst().get();
    }

    public void assignDriverToJob(Driver driver, Job job) {
        jobRepository.updateJobDriverAssignment(driver.getClientID());
        JobAssignmentInfo.displayDriverInfo(driver, job);
    }

    public void setJobAsPaid(int jobId) throws SQLException {
        Job byId = jobRepository.getById(jobId);
        JobInfo.displayJobInfo(byId);
    }

    public void setJobAsCancelled(int jobId) throws SQLException {
        Job byId = jobRepository.getById(jobId);
        JobInfo.displayJobInfo(byId);
    }

    public void setJobAsVerified(int jobId) throws SQLException {
        Job byId = jobRepository.getById(jobId);
        JobInfo.displayJobInfo(byId);
    }

    public void setJobAsRejected(int jobId) throws SQLException {
        Job byId = jobRepository.getById(jobId);
        JobInfo.displayJobInfo(byId);
    }

    /*
    public void createNewOrder(int clientId) {
        CreateJobDto dto = CreateNewJob.order();
        Job job = new Job(clientId, "NEWLY_ADDED", dto.description, dto.cargoType.toString(), dto.distance, dto.weight);
        jobRepository.insert(job);
        JobInfo.displayJobInfo(job);
    }

     */

    public void makePayment(User user) throws SQLException {
        List<Job> byUserId = jobRepository.getByUserId(user.getClientID());
        int choice = ListJobs.listAll(byUserId);
        Job job = byUserId.stream().filter(x -> x.getJob_Id() == choice).findFirst().get();

        boolean b = NewJobPayment.tryMakePayment(job);
        if (b) {
            setJobAsPaid(job.getJob_Id());
        } else {
            setJobAsCancelled(job.getJob_Id());
        }
    }

    public void setJobAsFinished(int jobId) throws SQLException {
        Job byId = jobRepository.getById(jobId);
        JobInfo.displayJobInfo(byId);
    }

    public void acceptJob(int jobId) throws SQLException {
        Job byId = jobRepository.getById(jobId);
        JobInfo.displayJobInfo(byId);
    }

    public Job listJobInRealization(int driverId) {
        Job assignedJob = jobRepository.getAssignedJob(driverId);
        JobInfo.displayJobInfo(assignedJob);
        return assignedJob;
    }

}
