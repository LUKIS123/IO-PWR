package pl.edu.pwr;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
///@SelectClasses({DriverRepositoryTest.class})
@SelectPackages({"pl.edu.pwr.application"})
public class Test {
    // todo działa
}
