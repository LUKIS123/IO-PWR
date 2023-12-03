package pl.edu.pwr.Repositories;

import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.enums.JobStatus;

import java.util.List;

public class JobRepository implements RepositoryInterface<Job> {
    @Override
    public List<Job> getAll() {
        return null;
    }

    @Override
    public Job getById(int id) {
        return null;
    }

    @Override
    public void insert(Job model) {

    }

    @Override
    public void update(int id, Job model) {

    }

    @Override
    public void delete(int id) {

    }

    public List<Job> getByUserId(int userId) {
        return null;
    }

    public List<Job> getByStatus(JobStatus status) {
        return null;
    }

    public void updateJobDriverAssignment(int driverId) {

    }
}
