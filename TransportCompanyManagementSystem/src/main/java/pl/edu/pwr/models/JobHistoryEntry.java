package pl.edu.pwr.models;

import pl.edu.pwr.models.enums.JobStatus;

import java.time.LocalDate;

public class JobHistoryEntry {
    private int id;
    private final int jobId;
    private final JobStatus oldStatus;
    private final JobStatus newStatus;
    private final LocalDate changeDate;

    public JobHistoryEntry(int jobId, JobStatus oldStatus, JobStatus newStatus) {
        this.jobId = jobId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changeDate = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public int getJobId() {
        return jobId;
    }

    public JobStatus getOldStatus() {
        return oldStatus;
    }

    public JobStatus getNewStatus() {
        return newStatus;
    }

    public LocalDate getChangeDate() {
        return changeDate;
    }

    @Override
    public String toString() {
        return "JobHistoryEntry{" +
                "id=" + id +
                ", jobId=" + jobId +
                ", oldStatus=" + oldStatus +
                ", newStatus=" + newStatus +
                ", changeDate=" + changeDate +
                '}';
    }
}
