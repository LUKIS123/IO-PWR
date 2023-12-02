package pl.edu.pwr.models;

import pl.edu.pwr.models.enums.UserType;

public class Administrator extends User {
    public Administrator(String username) {
        super(username);
        this.userType = UserType.ADMINISTRATOR;
    }

}
