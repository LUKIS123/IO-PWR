package pl.edu.pwr.Repositories;

import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.UserType;

import java.util.ArrayList;
import java.util.List;

public class DriverRepository extends DataStore implements RepositoryInterface<Driver> {

    @Override
    public List<Driver> getAll() {
        return driverList;
    }

    @Override
    public Driver getById(int id) {
        return driverList
                .stream()
                .filter(driver -> driver.getId() == id)
                .findFirst()
                .get();
    }

    @Override
    public void insert(Driver model) {
        driverList.add(model);
        userList.add(new User(model.getId(), model.getUsername(), UserType.DRIVER));
        ++USER_SEQUENCE;
    }

    @Override
    public void delete(int id) {
        Driver byId = getById(id);
        driverList.remove(byId);

        User user = userList
                .stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .get();
        userList.remove(user);
    }

    public List<Driver> getAvailableDrivers() {
        List<Driver> availableDrivers = new ArrayList<>();
        for (Driver driver : driverList) {
            boolean duringExecutionOfOrder = driver.isDuringExecutionOfOrder();
            boolean duringRest = driver.isDuringRest();
            if (!duringExecutionOfOrder && !duringRest) {
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }

}
