package pl.edu.pwr.models;

import pl.edu.pwr.models.enums.UserType;

public class User {
    private int id;
    UserType userType;
    private final String username;

    public User(int id, String username, UserType userType) {
        this.id = id;
        this.username = username;
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

}
