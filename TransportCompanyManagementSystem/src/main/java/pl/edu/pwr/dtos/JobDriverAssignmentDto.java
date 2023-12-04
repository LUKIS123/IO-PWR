package pl.edu.pwr.dtos;

import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;

public class JobDriverAssignmentDto {
    public JobDriverAssignmentDto(Driver driver, Job job) {
        this.driver = driver;
        this.job = job;
    }

    public Driver driver;
    public Job job;
}
