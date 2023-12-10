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

    public Driver listAvailableDrivers() {
        try {
            List<Driver> list = driverRepository.getAll().stream().filter(x -> (!x.isDuringExecutionOfOrder() && !x.isDuringRest())).toList();
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
