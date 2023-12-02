package pl.edu.pwr.Repositories;

import pl.edu.pwr.models.User;

import java.util.List;

public class UserRepository implements RepositoryInterface<User> {
    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public void insert(User model) {

    }

    @Override
    public void update(int id, User model) {

    }

    @Override
    public void delete(int id) {

    }

    public User getByUsername(String username) {
        return null;
    }
}
