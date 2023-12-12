package pl.edu.pwr.models;

import pl.edu.pwr.models.enums.UserType;
import pl.edu.pwr.views.driver.DriverView;

public class Driver extends User {
    public static DriverView driverView = new DriverView();
    private boolean duringExecutionOfOrder; // czy wykonuje zlecenia w tym momecnie
    private boolean duringRest; // czy ustawowa przerwa

    public Driver(int id, String username, boolean duringExecutionOfOrder, boolean duringRest) {
        super(id, username, UserType.DRIVER);
        this.duringExecutionOfOrder = duringExecutionOfOrder;
        this.duringRest = duringRest;
    }

    public Driver(int id, String username) {
        super(id, username, UserType.DRIVER);
        this.duringExecutionOfOrder = false;
        this.duringRest = false;
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
