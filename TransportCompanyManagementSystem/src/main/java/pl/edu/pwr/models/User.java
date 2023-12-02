package pl.edu.pwr.models;

import pl.edu.pwr.models.enums.UserType;

public class User {
    private int id;
    UserType userType;
    private final String username;

    public User(String username) {
        this.username = username;
        this.userType = UserType.CLIENT;
    }

    public int getId() {
        return id;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getUsername() {
        return username;
    }
}
