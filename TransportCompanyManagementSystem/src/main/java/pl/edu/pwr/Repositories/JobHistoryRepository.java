package pl.edu.pwr.Repositories;

import pl.edu.pwr.models.JobHistoryEntry;

import java.util.List;

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
}
