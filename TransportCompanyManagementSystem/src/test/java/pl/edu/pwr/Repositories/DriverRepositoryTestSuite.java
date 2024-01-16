package pl.edu.pwr.Repositories;

import org.junit.jupiter.api.Tag;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.platform.runner.JUnitPlatform;

@Suite
@SelectClasses({DriverRepositoryTest.class})
@IncludeTags({"getting-drivers", "repository-action"})
public class DriverRepositoryTestSuite {
}
