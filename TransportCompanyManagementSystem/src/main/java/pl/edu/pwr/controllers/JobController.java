package pl.edu.pwr.controllers;

import pl.edu.pwr.Repositories.JobHistoryRepository;
import pl.edu.pwr.Repositories.JobRepository;
import pl.edu.pwr.dtos.CreateJobDto;
import pl.edu.pwr.dtos.JobDriverAssignmentDto;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.JobHistoryEntry;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.CargoType;
import pl.edu.pwr.models.enums.JobStatus;

import java.util.List;
import java.util.Optional;

public class JobController {
    private final JobRepository jobRepository;
    private final JobHistoryRepository jobHistoryRepository;

    public JobController() {
        jobRepository = new JobRepository();
        jobHistoryRepository = new JobHistoryRepository();
    }

    public int listAllJobs() {
        List<Job> all = jobRepository.getAll();
        int i = Job.jobView.listAll(all);
        return i;
    }

    public Job listJobByStatus(JobStatus status) {
        List<Job> byStatus = jobRepository.getByStatus(status);
        byStatus.forEach(job -> job.setStatus(JobStatus.IN_VERIFICATION_PROCESS));
        int i = Job.jobView.listAll(byStatus);
        return byStatus
                .stream()
                .filter(x -> x.getJobId() == i)
                .findFirst()
                .get();
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

    public JobDriverAssignmentDto acceptForConsideration() {
        List<JobDriverAssignmentDto> list = jobRepository.getByStatusWithDriverSuggestion();
        int chosenJobId = Job.jobView.listOrdersWithDriverAssigment(list);

        JobDriverAssignmentDto dto = null;
        for (JobDriverAssignmentDto assignmentDto : list) {
            if (assignmentDto.job.getJobId() == chosenJobId) {
                dto = assignmentDto;
            }
        }
        return dto;
    }

    public void assignDriverToJob(Driver driver, Job job) {
        jobRepository.updateJobDriverAssignment(driver.getId(), job.getJobId());
        Job.jobView.displayDriverJobInfo(driver, job);
    }

    public void setJobAsPaid(int jobId) {
        Job byId = jobRepository.getById(jobId);
        byId.setStatus(JobStatus.PAID);
        jobHistoryRepository.insert(new JobHistoryEntry(jobId, JobStatus.NEWLY_ADDED, JobStatus.PAID));
        Job.jobView.displayJobInfo(byId);
    }

    public void setJobAsCancelled(int jobId) {
        Job byId = jobRepository.getById(jobId);
        byId.setStatus(JobStatus.CANCELLED);
        jobHistoryRepository.insert(new JobHistoryEntry(jobId, JobStatus.NEWLY_ADDED, JobStatus.CANCELLED));
        Job.jobView.displayJobInfo(byId);
    }

    public void setJobAsVerified(int jobId) {
        Job byId = jobRepository.getById(jobId);
        byId.setStatus(JobStatus.VERIFIED);
        jobHistoryRepository.insert(new JobHistoryEntry(jobId, JobStatus.IN_VERIFICATION_PROCESS, JobStatus.VERIFIED));
        Job.jobView.displayJobInfo(byId);
    }

    public void setJobAsRejected(int jobId) {
        Job byId = jobRepository.getById(jobId);
        byId.setStatus(JobStatus.REJECTED);
        jobHistoryRepository.insert(new JobHistoryEntry(jobId, JobStatus.IN_VERIFICATION_PROCESS, JobStatus.CANCELLED));
        Job.jobView.displayJobInfo(byId);
    }

    public void createNewOrder(int clientId) {
        CreateJobDto dto = Job.jobView.order();
        Job job = new Job(clientId, "NEWLY_ADDED", dto.cargoType.toString(), dto.distance, dto.weight);
        jobRepository.insert(job);
        Job.jobView.displayJobInfo(job);
    }

    public void makePayment(User user) {
        List<Job> byUserId = jobRepository.getByUserId(user.getId());
        int choice = Job.jobView.listAll(byUserId);

        Job chosenJob = null;
        for (Job job : byUserId) {
            if (job.getJobId() == choice) {
                chosenJob = job;
            }
        }

        calculateCost(chosenJob);

        boolean paymentConfirmed = Job.jobView.tryMakePayment(chosenJob);
        if (paymentConfirmed) {
            setJobAsPaid(chosenJob.getJobId());
            jobHistoryRepository.insert(new JobHistoryEntry(chosenJob.getJobId(), JobStatus.NEWLY_ADDED, JobStatus.PAID));
        } else {
            setJobAsCancelled(chosenJob.getJobId());
            jobHistoryRepository.insert(new JobHistoryEntry(chosenJob.getJobId(), JobStatus.NEWLY_ADDED, JobStatus.CANCELLED));
        }
    }

    public void setJobAsFinished(int jobId) {
        Job byId = jobRepository.getById(jobId);
        Job.jobView.displayJobInfo(byId);
    }

    public void acceptJob(int jobId) {
        Job byId = jobRepository.getById(jobId);
        Job.jobView.displayJobInfo(byId);
    }

    public Job listJobInRealization(int driverId) {
        Job assignedJob = null;
        try {
            assignedJob = jobRepository.getAssignedJob(driverId);
        } catch (Exception ignored) {
        }

        Job.jobView.displayJobInfo(assignedJob);
        return assignedJob;
    }

    public void calculateCost(Job job) {
        int weight = job.getWeight();
        int distance = job.getDistance();

        int cargoTypeAddedCost = 0;
        CargoType cargoType = job.getCargoType();
        switch (cargoType) {
            case HEAVY:
                cargoTypeAddedCost = 500;
                break;
            case HAZARDOUS:
                cargoTypeAddedCost = 1000;
                break;
            case FRAGILE:
                cargoTypeAddedCost = 700;
                break;
            default:
                cargoTypeAddedCost = 0;
                break;
        }

        job.setCost((int) (1.2 * distance + cargoTypeAddedCost + 0.8 * weight));
    }

}
