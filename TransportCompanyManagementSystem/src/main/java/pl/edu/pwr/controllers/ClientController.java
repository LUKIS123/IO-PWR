package pl.edu.pwr.controllers;

import pl.edu.pwr.Repositories.JobRepository;
import pl.edu.pwr.dtos.CreateJobDto;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.views.client.ClientIndex;
import pl.edu.pwr.views.client.CreateOrder;
import pl.edu.pwr.views.client.MakePayment;
import pl.edu.pwr.views.job.JobInfo;
import pl.edu.pwr.views.job.JobsIndex;

import java.util.List;
import java.util.Optional;

public class ClientController {
    private final JobRepository jobRepository;
    private final User user;

    public ClientController(User user) {
        this.user = user;
        jobRepository = new JobRepository();
    }

    public void index() {
        int choice = ClientIndex.clientMenu();
    }

    public void createNewOrder() {
        CreateJobDto dto = CreateOrder.order();
    }

    public void makePayment() {
        List<Job> byUserId = jobRepository.getByUserId(user.getId());
        Optional<Integer> i = MakePayment.tryMakePayment(byUserId);
    }

    public void listOrder() {
        List<Job> byUserId = jobRepository.getByUserId(user.getId());
        Optional<Integer> index = JobsIndex.listOrders(byUserId);
        if (index.isPresent()) {
            Optional<Job> first = byUserId.stream().filter(x -> x.getId() == index.get()).findFirst();
            JobInfo.displayJobInfo(first.get());
        }
    }

}
