package pl.edu.pwr.models;

import pl.edu.pwr.models.enums.UserType;
import pl.edu.pwr.views.driver.DriverView;

public class Driver extends User {
    private boolean duringExecutionOfOrder; // czy wykonuje zlecenia w tym momecnie
    private boolean duringRest; // czy ustawowa przerwa

    public static DriverView driverView = new DriverView();

    public Driver(String username) {
        super(username);
        this.userType = UserType.DRIVER;
    }

    public Driver(String username, boolean duringExecutionOfOrder, boolean duringRest) {
        super(username);
        this.duringExecutionOfOrder = duringExecutionOfOrder;
        this.duringRest = duringRest;
    }

    public boolean isDuringExecutionOfOrder() {
        return duringExecutionOfOrder;
    }

    public boolean isDuringRest() {
        return duringRest;
    }

    public void setDuringExecutionOfOrder(boolean duringExecutionOfOrder) {
        this.duringExecutionOfOrder = duringExecutionOfOrder;
    }

    public void setDuringRest(boolean duringRest) {
        this.duringRest = duringRest;
    }

}
