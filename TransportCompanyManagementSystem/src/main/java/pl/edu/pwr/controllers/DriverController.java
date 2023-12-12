package pl.edu.pwr.controllers;

import pl.edu.pwr.Repositories.DriverRepository;
import pl.edu.pwr.models.Driver;

import java.sql.SQLException;
import java.util.List;

public class DriverController {
    private final DriverRepository driverRepository;

    public DriverController() {
        driverRepository = new DriverRepository();
    }

    public void setStatusOnShift(int id) {
        try {
            Driver byId = driverRepository.getById(id);
            byId.setDuringRest(false);
            driverRepository.update(id, byId);
            Driver.driverView.makeAction(byId);

        } catch (SQLException e) {

        }
    }

    public void setStatusDuringRest(int id) {
        try {
            Driver byId = driverRepository.getById(id);
            byId.setDuringRest(true);
            driverRepository.update(id, byId);
            Driver.driverView.makeAction(byId);
        } catch (SQLException e) {

        }
    }

    public void acceptJob(int id) {
        try {
            Driver byId = driverRepository.getById(id);
            byId.setDuringExecutionOfOrder(true);
            driverRepository.update(id, byId);
            Driver.driverView.makeAction(byId);
        } catch (SQLException e) {

        }
    }

    public void finishJob(int id) {
        try {
            Driver byId = driverRepository.getById(id);
            byId.setDuringExecutionOfOrder(false);
            driverRepository.update(id, byId);
            Driver.driverView.makeAction(byId);
        } catch (SQLException e) {

        }
    }

    public Driver listAvailableDrivers() {
        try {
            List<Driver> list = driverRepository.getAvailableDrivers();
            int id = Driver.driverView.listDrivers(list);
            return list.stream().filter(x -> x.getId() == id).findFirst().get();
        } catch (SQLException e) {
            return null;
        }
    }

    public void displayDriverInfo(int driverId) {
        try {
            Driver byId = driverRepository.getById(driverId);
            Driver.driverView.displayDriverInfo(byId);
        } catch (SQLException e) {
        }
    }

}
