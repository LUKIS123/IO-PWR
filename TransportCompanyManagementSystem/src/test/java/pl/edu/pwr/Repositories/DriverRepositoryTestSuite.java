package pl.edu.pwr.Repositories;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({DriverRepositoryTest.class})
@IncludeTags({"getting-drivers", "repository-action"})
public class DriverRepositoryTestSuite {
}
