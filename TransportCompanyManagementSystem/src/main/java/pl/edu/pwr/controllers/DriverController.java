package pl.edu.pwr.controllers;

import pl.edu.pwr.Repositories.DriverRepository;
import pl.edu.pwr.models.Driver;

import java.util.List;

public class DriverController {
    private final DriverRepository driverRepository;

    public DriverController() {
        driverRepository = new DriverRepository();
    }

    public void setStatusOnShift(int id) {
        Driver byId = driverRepository.getById(id);
        byId.setDuringRest(false);
        Driver.driverView.makeAction(byId);
    }

    public void setStatusDuringRest(int id) {
        Driver byId = driverRepository.getById(id);
        byId.setDuringRest(true);
        Driver.driverView.makeAction(byId);
    }

    public void acceptJob(int id) {
        Driver byId = driverRepository.getById(id);
        byId.setDuringExecutionOfOrder(true);
        Driver.driverView.makeAction(byId);
    }

    public void finishJob(int id) {
        Driver byId = driverRepository.getById(id);
        byId.setDuringExecutionOfOrder(false);
        Driver.driverView.makeAction(byId);
    }

    public Driver listAvailableDrivers() {
        List<Driver> list = driverRepository.getAvailableDrivers();
        int chosenDriverId = Driver.driverView.listDrivers(list);

        Driver chosenDriver = null;
        for (Driver driver : list) {
            int driverId = driver.getId();
            if (driverId == chosenDriverId) {
                chosenDriver = driver;
            }
        }
        return chosenDriver;
    }

    public void displayDriverInfo(int driverId) {
        Driver byId = driverRepository.getById(driverId);
        Driver.driverView.displayDriverInfo(byId);
    }

}
