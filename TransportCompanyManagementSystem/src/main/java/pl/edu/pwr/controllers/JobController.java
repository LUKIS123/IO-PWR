package pl.edu.pwr.controllers;

import pl.edu.pwr.Repositories.JobRepository;
import pl.edu.pwr.dtos.CreateJobDto;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.views.job.*;

import java.util.List;
import java.util.Optional;

public class JobController {
    private final JobRepository jobRepository;

    public JobController() {
        jobRepository = new JobRepository();
    }

    public int listAllJobs() {
        List<Job> all = jobRepository.getAll();
        return 0;
    }

    public int listJobByStatus(JobStatus status) {
        List<Job> byStatus = jobRepository.getByStatus(status);
        return 0;
    }

    public int listJobsByOwner(int userId) {
        List<Job> byUserId = jobRepository.getByUserId(userId);

        Optional<Integer> index = JobsIndex.listOrders(byUserId);
        if (index.isPresent()) {
            Optional<Job> first = byUserId.stream().filter(x -> x.getId() == index.get()).findFirst();
            JobInfo.displayJobInfo(first.get());
        }
        return 0;
    }

    public void assignDriverToJob(int driverId) {
        jobRepository.updateJobDriverAssignment(driverId);
    }

    public void addNewJob(int clientId, CreateJobDto createJobDto) {
        Job job = new Job(clientId, "NEWLY_ADDED", createJobDto.description, createJobDto.cargoType.toString(), createJobDto.distance, createJobDto.weight);
        jobRepository.insert(job);
    }

    public void setJobAsPaid(int jobId) {

    }

    public void setJobAsCancelled(int jobId) {

    }

    public void setJobVerificationProcessStart(int jobId) {

    }

    public void setJobAsVerified(int jobId) {

    }

    public void setJobAsRejected(int jobId) {

    }

    public void setJobAsInProgress(int jobId) {

    }

    public void setJobAsFinished(int jobId) {

    }

    public void createNewOrder() {
        CreateJobDto dto = CreateNewJob.order();
    }

    public void makePayment(User user) {
        List<Job> byUserId = jobRepository.getByUserId(user.getId());
        Optional<Integer> i = NewJobPayment.tryMakePayment(byUserId);
    }

    public void acceptForConsideration() {
        List<Job> list = jobRepository.getAll().stream().filter(x -> x.status == JobStatus.PAID).toList();
        int i = CheckoutNewJobs.listOrders(list);
    }

}
