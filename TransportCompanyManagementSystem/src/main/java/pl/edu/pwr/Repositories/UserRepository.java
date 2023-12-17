package pl.edu.pwr.Repositories;

import pl.edu.pwr.models.User;

import java.util.List;
import java.util.Objects;

public class UserRepository extends DataStore implements RepositoryInterface<User> {
    @Override
    public List<User> getAll() {
        return userList;
    }

    @Override
    public User getById(int id) {
        return userList
                .stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .get();
    }

    @Override
    public void insert(User model) {
        userList.add(model);
        ++USER_SEQUENCE;
    }

    @Override
    public void delete(int id) {
        User byId = getById(id);
        userList.remove(byId);
    }

    public User getByUsername(String username) {
        for (User user : userList) {
            if (Objects.equals(user.getUsername(), username)) {
                return user;
            }
        }
        return null;
    }
}
