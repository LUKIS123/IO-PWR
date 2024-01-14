package pl.edu.pwr.Repositories;

import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.JobHistoryEntry;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.CargoType;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.models.enums.UserType;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    // todo: dodac elementy
    public static int JOB_SEQUENCE = 1;
    public static int USER_SEQUENCE = 1;
    public static int HISTORY_ENTRY_SEQUENCE = 1;
    public static List<Job> jobList = new ArrayList<>(
            List.of(new Job(JOB_SEQUENCE++, -1, 1, CargoType.HEAVY.toString(), JobStatus.PAID.toString(), 300, 1500, true),
                    new Job(JOB_SEQUENCE++, -1, 1, CargoType.HEAVY.toString(), JobStatus.NEWLY_ADDED.toString(), 300, 1500, false))
    );
    // todo: uwaga aby ID zgadzały sie z userList
    protected static List<Driver> driverList = new ArrayList<>(List.of(
            new Driver(3, "Adam"),
            new Driver(4, "Anton")
    ));
    // todo: uwaga aby ID Driverów zgadzały sie z driverList
    protected static List<User> userList = new ArrayList<>(List.of(
            new User(USER_SEQUENCE++, "Andrzej", UserType.CLIENT),
            new User(USER_SEQUENCE++, "Aleksander", UserType.ADMINISTRATOR),
            new User(USER_SEQUENCE++, "Adam", UserType.DRIVER),
            new User(USER_SEQUENCE++, "Anton", UserType.DRIVER))
    );
    protected static List<JobHistoryEntry> historyEntryList = new ArrayList<>();

}
