package pl.edu.pwr.models;

import pl.edu.pwr.models.enums.UserType;

public class Administrator extends User {
    public Administrator(int id, String username) {
        super(id, username, UserType.ADMINISTRATOR);
    }
}
