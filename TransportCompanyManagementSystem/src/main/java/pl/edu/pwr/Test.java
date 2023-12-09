package pl.edu.pwr;

import pl.edu.pwr.Repositories.DriverRepository;
import pl.edu.pwr.Repositories.JobRepository;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.enums.CargoType;

import java.sql.SQLException;

import static pl.edu.pwr.models.enums.CargoType.HEAVY;
import static pl.edu.pwr.models.enums.CargoType.LIGHT;
import static pl.edu.pwr.models.enums.JobStatus.PAID;

public class Test {
    void test1() throws SQLException {
        var repo = new DriverRepository();
        Driver tester = new Driver("Albert444",false,false);
        repo.insert(tester);
    }

    void test2() throws SQLException {
        var repo = new DriverRepository();
        repo.delete(10);
    }

    void test3() throws SQLException {
        var repo = new DriverRepository();
        Driver tester = new Driver("Albert69",false,false);
        repo.update(10, tester);
    }

    void test4() throws SQLException {
        var repo = new DriverRepository();
        repo.getAvailableDrivers();
    }

    void test5() throws SQLException {
        var repo = new JobRepository();
        repo.getAll();
    }

    void test6() throws SQLException {
        var repo = new JobRepository();
        repo.getById(3);
    }

    void test7() throws SQLException {
        var repo = new JobRepository();
        Job j = new Job(4,3, HEAVY.toString(),PAID.toString(),30000,80,true
        );
        repo.insert(j);
    }

    void test8() throws SQLException {
        var repo = new JobRepository();
        Job j = new Job(4,3, LIGHT.toString(),PAID.toString(),30000,80,true
        );
        repo.update(5,j);
    }
}
