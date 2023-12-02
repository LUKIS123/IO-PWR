package pl.edu.pwr.models;

public class Job {
    private int id;
    private int clientId;
    public String status;
    public boolean isPaid = false;
    private final String description;
    private final String cargoType;
    private final double distance;
    private final double weight;

    public Job(int clientId, String status, String description, String cargoType, double distance, double weight) {
        this.clientId = clientId;
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

    public String getCargoType() {
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
