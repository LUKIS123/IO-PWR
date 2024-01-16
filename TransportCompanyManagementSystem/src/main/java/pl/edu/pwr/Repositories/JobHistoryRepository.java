package pl.edu.pwr.Repositories;

import pl.edu.pwr.models.JobHistoryEntry;
import pl.edu.pwr.models.enums.JobStatus;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class JobHistoryRepository extends DataStore implements RepositoryInterface<JobHistoryEntry> {
    @Override
    public List<JobHistoryEntry> getAll() {
        return historyEntryList;
    }

    @Override
    public JobHistoryEntry getById(int id) {
        return historyEntryList
                .stream()
                .filter(h -> h.getId() == id)
                .findFirst()
                .get();
    }

    @Override
    public void insert(JobHistoryEntry model) {
        historyEntryList.add(model);
        ++HISTORY_ENTRY_SEQUENCE;
    }

    @Override
    public void delete(int id) {
        JobHistoryEntry byId = getById(id);
        historyEntryList.remove(byId);
    }

    public List<JobHistoryEntry> getByJobId(int jobId) {
        return historyEntryList
                .stream()
                .filter(h -> h.getJobId() == jobId)
                .toList();
    }

    public JobHistoryEntry getByJobIdLatest(int jobId) {
        List<JobHistoryEntry> byJobId = getByJobId(jobId);
        try {
            return byJobId
                    .stream()
                    .max(Comparator.comparing(JobHistoryEntry::getChangeDate))
                    .get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

}
