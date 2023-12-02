package pl.edu.pwr.models;

import pl.edu.pwr.models.enums.UserType;

import java.time.Duration;
import java.time.LocalDateTime;

public class Driver extends User {
    private boolean duringExecutionOfOrder;
    private boolean duringRest;
    private LocalDateTime beginShift;
    private LocalDateTime endShift;

    public Driver(String username) {
        super(username);
        this.userType = UserType.DRIVER;
    }

    public boolean isDuringExecutionOfOrder() {
        return duringExecutionOfOrder;
    }

    public boolean isDuringRest() {
        return duringRest;
    }

    public LocalDateTime getBeginShift() {
        return beginShift;
    }

    public LocalDateTime getEndShift() {
        return endShift;
    }

    public void setDuringExecutionOfOrder(boolean duringExecutionOfOrder) {
        this.duringExecutionOfOrder = duringExecutionOfOrder;
    }

    public void setDuringRest(boolean duringRest) {
        this.duringRest = duringRest;
    }

    public void setBeginShift(LocalDateTime beginShift) {
        this.beginShift = beginShift;
    }

    public void setEndShift(LocalDateTime endShift) {
        this.endShift = endShift;
    }

    public long getWorkingHours(LocalDateTime currentTime) {
        Duration duration = Duration.between(beginShift, currentTime);
        return duration.getSeconds() / 3600;
    }
}
