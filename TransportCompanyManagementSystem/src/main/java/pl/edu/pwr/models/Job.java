package pl.edu.pwr.models;

public class Job {
    private int id;
    public JobStatus status;
    private String description;

    // todo
    // dodac enumy rodzaj toaru,
    private double distance;
    private double weight;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getDistance() {
        return distance;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", distance=" + distance +
                ", weight=" + weight +
                '}';
    }
}
