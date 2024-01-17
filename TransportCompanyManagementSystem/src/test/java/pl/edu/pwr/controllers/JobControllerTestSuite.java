package pl.edu.pwr.controllers;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({JobControllerTest.class})
// @IncludeTags({"create-new-order", "job-in-realization", "calculate-cost"})
@IncludeTags("setting-job-status")
public class JobControllerTestSuite {
}
