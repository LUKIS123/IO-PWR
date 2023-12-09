package pl.edu.pwr;

import pl.edu.pwr.Repositories.DriverRepository;
import pl.edu.pwr.models.Driver;

import java.sql.SQLException;

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
}
