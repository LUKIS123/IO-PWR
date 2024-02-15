package pl.edu.pwr.fitnesse;

import fit.Fixture;
import pl.edu.pwr.Main;
import pl.edu.pwr.Repositories.JobRepository;

public class Setup extends Fixture {
    public static Main main;
    public static JobRepository jobRepository;

    public static void setUp() {
        main = new Main();
    }
}
