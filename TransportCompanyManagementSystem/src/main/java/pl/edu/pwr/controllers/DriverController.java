package pl.edu.pwr.controllers;

import pl.edu.pwr.Repositories.DriverRepository;
import pl.edu.pwr.models.Driver;

import java.time.LocalDateTime;
import java.util.List;

public class DriverController {
    private final DriverRepository driverRepository;

    public DriverController() {
        driverRepository = new DriverRepository();
    }

    public void setStatusOnShift(int id) {
        Driver byId = driverRepository.getById(id);
        byId.setBeginShift(LocalDateTime.now());
        driverRepository.update(id, byId);
        Driver.driverView.makeAction(byId);
    }

    public void setStatusDuringRest(int id) {
        Driver byId = driverRepository.getById(id);
        byId.setDuringRest(true);
        driverRepository.update(id, byId);
        Driver.driverView.makeAction(byId);
    }

    public Driver listAvailableDrivers() {
        List<Driver> list = driverRepository.getAll().stream().filter(x -> (!x.isDuringExecutionOfOrder() && !x.isDuringRest())).toList();
        int id = Driver.driverView.listDrivers(list);
        return list.stream().filter(x -> x.getId() == id).findFirst().get();
    }

    public void displayDriverInfo(int driverId) {
        Driver byId = driverRepository.getById(driverId);
        Driver.driverView.displayDriverInfo(byId);
    }
}
