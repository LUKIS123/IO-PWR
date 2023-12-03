package pl.edu.pwr.models;

import pl.edu.pwr.models.enums.CargoType;
import pl.edu.pwr.models.enums.JobStatus;

public class Job {
    private int id;
    private final int clientId;
    public JobStatus status;
    public boolean isPaid = false;
    private final String description;
    private final CargoType cargoType;
    private final double distance;
    private final double weight;

    public Job(int clientId, String status, String description, String cargoType, double distance, double weight) {
        this.clientId = clientId;
        this.status = mapStatusToEnum(status);
        this.description = description;
        this.cargoType = mapTypeToEnum(cargoType);
        this.distance = distance;
        this.weight = weight;
    }

    private JobStatus mapStatusToEnum(String s) {
        switch (s) {
            case "NEWLY_ADDED" -> {
                return JobStatus.NEWLY_ADDED;
            }
            case "PAID" -> {
                return JobStatus.PAID;
            }
            case "CANCELLED" -> {
                return JobStatus.CANCELLED;
            }
            case "READY_TO_VERIFY" -> {
                return JobStatus.READY_TO_VERIFY;
            }
            case "VERIFIED" -> {
                return JobStatus.VERIFIED;
            }
            case "REJECTED" -> {
                return JobStatus.REJECTED;
            }
            case "IN_PROGRESS" -> {
                return JobStatus.IN_PROGRESS;
            }
            case "FINISHED" -> {
                return JobStatus.FINISHED;
            }
            default -> {
                return JobStatus.UNKNOWN;
            }
        }
    }

    private CargoType mapTypeToEnum(String s) {
        switch (s) {
            case "LIGHT" -> {
                return CargoType.LIGHT;
            }
            case "HEAVY" -> {
                return CargoType.HEAVY;
            }
            case "FRAGILE" -> {
                return CargoType.FRAGILE;
            }
            case "HAZARDOUS" -> {
                return CargoType.HAZARDOUS;
            }
            default -> {
                return CargoType.NONE;
            }
        }
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

    public int getClientId() {
        return clientId;
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
