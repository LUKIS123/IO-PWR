package pl.edu.pwr.controllers;

import pl.edu.pwr.Repositories.DriverRepository;
import pl.edu.pwr.Repositories.JobRepository;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.views.application.DriverAppIndex;
import pl.edu.pwr.views.driver.ListDrivers;

import java.util.List;

public class DriverController {
    private final JobRepository jobRepository;
    private final DriverRepository driverRepository;

    public DriverController() {
        driverRepository = new DriverRepository();
        jobRepository = new JobRepository();
    }

    public void index() {
        int choice = DriverAppIndex.driverMenu();

    }

    public void acceptJob(int jobId) {

    }

    public void setStatusJobFinished() {

    }

    public void setStatusOnShift() {

    }

    public void setStatusDuringRest() {

    }

    public void setStatusWorking() {

    }

    public void listAvailableDrivers(int orderId) {
        List<Driver> list = driverRepository.getAll().stream().filter(x -> (!x.isDuringExecutionOfOrder() && !x.isDuringRest())).toList();
        int id = ListDrivers.listDrivers(list);
    }

}
