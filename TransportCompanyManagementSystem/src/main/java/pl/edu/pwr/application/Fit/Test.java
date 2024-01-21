package pl.edu.pwr.application.Fit;


import fit.ColumnFixture;
import pl.edu.pwr.Repositories.JobRepository;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.User;
import pl.edu.pwr.models.enums.CargoType;
import pl.edu.pwr.models.enums.JobStatus;
import pl.edu.pwr.models.enums.UserType;

import java.util.List;

public class Test extends ColumnFixture {

    /*
    public int a;
    public int b;

    public int dodaj() {
        return a + b;
    }
     */

    public int weight;
    public int distance;

    public int test_calculate_cost() {
        JobController jobController = new JobController();
        Job job = new Job(9000,
                3,
                3,
                CargoType.HEAVY.toString(),
                JobStatus.NEWLY_ADDED.toString(),
                distance,
                weight,
                false
        );

        jobController.calculateCost(job);
        return job.getCost();
    }

    public int userIdToFind;
    public boolean insert;


    public Integer metoda() {
        JobRepository jobRepository = new JobRepository();
        jobRepository.getAll().clear();

        Job job = new Job(9000,
                8,
                userIdToFind,
                CargoType.HEAVY.toString(),
                JobStatus.NEWLY_ADDED.toString(),
                100,
                100,
                false
        );

        if(insert){
            jobRepository.insert(job);
        }
        List<Job> byUserId = jobRepository.getByUserId(userIdToFind);
        if(byUserId.isEmpty()){
            return null;
        }

        return byUserId.get(0).getClientId();
    }


    public String make_payment_test(){
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

        jobController.makePayment(user);

        List<Job> all = jobController
                .getJobRepository()
                .getAll();

        if(all.isEmpty()){
            return "FAIL";
        }

        return all.get(0).getStatus().toString();
    }

}
