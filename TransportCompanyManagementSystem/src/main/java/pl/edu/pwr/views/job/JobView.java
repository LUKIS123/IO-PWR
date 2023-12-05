package pl.edu.pwr.views.job;

import pl.edu.pwr.dtos.CreateJobDto;
import pl.edu.pwr.dtos.JobDriverAssignmentDto;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;

import java.util.List;
import java.util.Optional;

public class JobView {

    public boolean tryMakePayment(Job job) {
        return false;
    }

    public int listAll(List<Job> jobs) {
        return 0;
    }

    public int verifyView(Job job, Driver driver) {
        return 0;
    }

    public void displayJobInfo(Job job) {
    }

    public void displayDriverJobInfo(Driver driver, Job job) {
    }

    public CreateJobDto order() {
        return null;
    }

    public Optional<Integer> listOrders(List<Job> byUserId) {
        return Optional.empty();
    }

    public int listOrdersWithDriverAssigment(List<JobDriverAssignmentDto> jobList) {
        return 0;
    }
}
