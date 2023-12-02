package pl.edu.pwr.Repositories;

import pl.edu.pwr.models.JobHistoryEntry;

import java.util.List;

public class JobHistoryRepository implements RepositoryInterface<JobHistoryEntry>{
    @Override
    public List<JobHistoryEntry> getAll() {
        return null;
    }

    @Override
    public JobHistoryEntry getById(int id) {
        return null;
    }

    @Override
    public void insert(JobHistoryEntry model) {

    }

    @Override
    public void update(int id, JobHistoryEntry model) {

    }

    @Override
    public void delete(int id) {

    }
}
