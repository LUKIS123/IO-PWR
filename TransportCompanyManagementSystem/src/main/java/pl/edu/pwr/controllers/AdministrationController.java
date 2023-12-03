package pl.edu.pwr.controllers;

import pl.edu.pwr.Repositories.DriverRepository;
import pl.edu.pwr.Repositories.JobRepository;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.views.administrator.AdministratorIndex;
import pl.edu.pwr.views.administrator.NewOrders;
import pl.edu.pwr.views.administrator.OrderVerification;
import pl.edu.pwr.views.driver.ListDrivers;

import java.util.List;

public class AdministrationController {
    private final JobRepository jobRepository;
    private final DriverRepository driverRepository;
    private final User user;

    public AdministrationController(User user) {
        this.user = user;
        this.jobRepository = new JobRepository();
        driverRepository = new DriverRepository();
    }

    public void index() {
        int choice = AdministratorIndex.adminMenu();
    }

    public void acceptForConsideration() {
        List<Job> list = jobRepository.getAll().stream().filter(x -> x.status == JobStatus.PAID).toList();
        int i = NewOrders.listOrders(list);
    }

    public void startVerificationProcess(int orderId) {
        boolean b = OrderVerification.verifyView(null, null);
    }

    public void listAvailableDrivers(int orderId) {
        List<Driver> list = driverRepository.getAll().stream().filter(x -> (!x.isDuringExecutionOfOrder() && !x.isDuringRest())).toList();
        int id = ListDrivers.listDrivers(list);
    }



}
