package pl.edu.pwr.fitnesse;

import fit.ColumnFixture;
import pl.edu.pwr.Repositories.JobRepository;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.CargoType;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.models.enums.UserType;

import java.util.List;

import static pl.edu.pwr.Repositories.JobRepository.*;

public class TestMakePayment extends ColumnFixture {

    int calculatedCost;
    int expectedCost;


    int userIdToFind;


    boolean paymentConfirmed;

    public String makePaymentTest() {
        User user = new User(999, "test", UserType.CLIENT);
        Job job = new Job(9000,
                -1,
                user.getId(),
                CargoType.HEAVY.toString(),
                JobStatus.NEWLY_ADDED.toString(),
                1000,
                100,
                false);

        JobController jobController = new JobController();
        jobController.getJobRepository().getAll().clear();

        jobController.getJobRepository().insert(job);

        jobController.makePayment(user, paymentConfirmed);

        List<Job> all = jobController
                .getJobRepository()
                .getAll();

        if (all.isEmpty()) {
            return "FAIL";
        }

        return all.get(0).getStatus().toString();

//
//        System.out.println(Setup.jobRepository.getById(1).getCost());
//        return Setup.jobRepository.getById(1).getCost() == expectedCost;


//        Class<?> JobController = JobController.class;
//        try {
//            System.out.println(SetUp.callParseMethod(JobController,"calculateJobCost"));
//
//
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

    }

}
