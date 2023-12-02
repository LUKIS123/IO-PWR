package pl.edu.pwr.models;

import pl.edu.pwr.models.enums.CargoType;
import pl.edu.pwr.models.enums.JobStatus;

public class Job {
    private int id;
    public JobStatus status;
    public boolean isPaid = false;
    private final String description;
    private final CargoType cargoType;
    private final double distance;
    private final double weight;

    public Job(JobStatus status, String description, CargoType cargoType, double distance, double weight) {
        this.status = status;
        this.description = description;
        this.cargoType = cargoType;
        this.distance = distance;
        this.weight = weight;
    }

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

    public CargoType getCargoType() {
        return cargoType;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", cargoType=" + cargoType +
                ", distance=" + distance +
                ", weight=" + weight +
                '}';
    }
}
