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
import pl.edu.pwr.views.job.JobView;

import java.util.List;
import java.util.Optional;

public class JobController {
    private final JobRepository jobRepository;
    private final JobHistoryRepository jobHistoryRepository;

    public JobController(JobRepository jobRepository, JobHistoryRepository jobHistoryRepository) {
        this.jobRepository = jobRepository;
        this.jobHistoryRepository = jobHistoryRepository;
    }

    public JobController() {
        jobRepository = new JobRepository();
        jobHistoryRepository = new JobHistoryRepository();
    }

    public int listAllJobs() {
        List<Job> all = jobRepository.getAll();
        int i = JobView.listAll(all);
        return i;
    }

    public List<Job> listJobByStatus(JobStatus status) {
        return jobRepository.getByStatus(status);
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

        if (list.isEmpty()) {
            System.out.println("Brak zlecen... Przyjmij nowe zlecenia...");
            return null;
        }

        int chosenJobId = Job.jobView.listOrdersWithDriverAssigment(list);

        JobDriverAssignmentDto dto = null;
        for (JobDriverAssignmentDto assignmentDto : list) {
            int jobId = assignmentDto.job.getJobId();
            if (jobId == chosenJobId) {
                dto = assignmentDto;
            }
        }
        return dto;
    }

    public void assignDriverToJob(Driver driver, Job job) {
        int driverId = driver.getId();
        int jobId = job.getJobId();
        jobRepository.updateJobDriverAssignment(driverId, jobId);
        Job.jobView.displayDriverJobInfo(driver, job);
    }

    // todo
    public void setJobAsPaid(int jobId) {
        Job byId = jobRepository.getById(jobId);
        byId.setStatus(JobStatus.PAID);
        jobHistoryRepository.insert(new JobHistoryEntry(jobId, JobStatus.NEWLY_ADDED, JobStatus.PAID));
        Job.jobView.displayJobInfo(byId);
    }

    // todo
    public void setJobAsCancelled(int jobId) {
        Job byId = jobRepository.getById(jobId);
        byId.setStatus(JobStatus.CANCELLED);
        jobHistoryRepository.insert(new JobHistoryEntry(jobId, JobStatus.NEWLY_ADDED, JobStatus.CANCELLED));
        Job.jobView.displayJobInfo(byId);
    }

    // todo
    public void setJobAsVerified(int jobId) {
        Job byId = jobRepository.getById(jobId);
        byId.setStatus(JobStatus.VERIFIED);
        jobHistoryRepository.insert(new JobHistoryEntry(jobId, JobStatus.IN_VERIFICATION_PROCESS, JobStatus.VERIFIED));
        Job.jobView.displayJobInfo(byId);
    }

    // todo
    public void setJobAsRejected(int jobId) {
        Job byId = jobRepository.getById(jobId);
        byId.setStatus(JobStatus.REJECTED);
        jobHistoryRepository.insert(new JobHistoryEntry(jobId, JobStatus.IN_VERIFICATION_PROCESS, JobStatus.REJECTED));
        Job.jobView.displayJobInfo(byId);
    }

    // todo
    public void createNewOrder(int clientId) {
        CreateJobDto dto = JobView.order();
        Job job = new Job(clientId, "NEWLY_ADDED", dto.cargoType.toString(), dto.distance, dto.weight);
        jobRepository.insert(job);
        Job.jobView.displayJobInfo(job);
    }

    public void createNewOrder(int clientId, CreateJobDto dto) {
        Job job = new Job(clientId, "NEWLY_ADDED", dto.getCargoType().toString(), dto.getDistance(), dto.getWeight());
        jobRepository.insert(job);
        Job.jobView.displayJobInfo(job);
    }

    public void makePayment(User user) {
        List<Job> byUserId = jobRepository.getByUserId(user.getId());
        int choice = JobView.listAll(byUserId);

        Job chosenJob = null;
        for (Job job : byUserId) {
            int jobId = job.getJobId();
            if (jobId == choice) {
                chosenJob = job;
                break;
            }
        }

        if (chosenJob == null) {
            System.out.println("Nie ma takiego zlecenia");
            return;
        }

        calculateCost(chosenJob);

        boolean paymentConfirmed = JobView.tryMakePayment(chosenJob);
        int chosenJobJobId = chosenJob.getJobId();
        if (paymentConfirmed) {
            setJobAsPaid(chosenJobJobId);
            jobHistoryRepository.insert(new JobHistoryEntry(chosenJobJobId, JobStatus.NEWLY_ADDED, JobStatus.PAID));
        } else {
            setJobAsCancelled(chosenJobJobId);
            jobHistoryRepository.insert(new JobHistoryEntry(chosenJobJobId, JobStatus.NEWLY_ADDED, JobStatus.CANCELLED));
        }
    }

    public void setJobAsFinished(int jobId) {
        Job byId = jobRepository.getById(jobId);
        Job.jobView.displayJobInfo(byId);
        byId.setStatus(JobStatus.FINISHED);
    }

    public void acceptJob(int jobId) {
        Job byId = jobRepository.getById(jobId);
        Job.jobView.displayJobInfo(byId);
        byId.setStatus(JobStatus.IN_PROGRESS);
    }

    public Job listJobInRealization(int driverId) {
        Job assignedJob = null;
        try {
            assignedJob = jobRepository.getAssignedJob(driverId);
        } catch (Exception ignored) {
        }

        if (assignedJob != null) {
            Job.jobView.displayJobInfo(assignedJob);
        }
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

    public JobRepository getJobRepository() {
        return jobRepository;
    }

    public JobHistoryRepository getJobHistoryRepository() {
        return jobHistoryRepository;
    }
}
