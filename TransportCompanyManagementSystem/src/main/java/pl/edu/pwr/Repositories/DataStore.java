package pl.edu.pwr.Repositories;

import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.JobHistoryEntry;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.CargoType;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.models.enums.UserType;

import java.util.List;

public class DataStore {
    // todo: dodac elementy
    public static int JOB_SEQUENCE = 1;
    public static int USER_SEQUENCE = 1;
    public static int HISTORY_ENTRY_SEQUENCE = 1;
    protected static List<Job> jobList = List.of(
            new Job(JOB_SEQUENCE++, 1, CargoType.HEAVY.toString(), JobStatus.PAID.toString(), 300, 1500, true)
    );
    // todo: uwaga aby ID zgadzały sie z userList
    protected static List<Driver> driverList = List.of(
            new Driver(3, "Adam")
    );
    // todo: uwaga aby ID Driverów zgadzały sie z driverList
    protected static List<User> userList = List.of(
            new User(USER_SEQUENCE++, "Andrzej", UserType.CLIENT),
            new User(USER_SEQUENCE++, "Aleksander", UserType.ADMINISTRATOR),
            new User(USER_SEQUENCE++, "Adam", UserType.DRIVER)
    );
    protected static List<JobHistoryEntry> historyEntryList = List.of();
}
