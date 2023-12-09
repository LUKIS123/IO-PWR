package pl.edu.pwr.Repositories;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryInterface<T> {
    List<T> getAll() throws SQLException;

    T getById(int id) throws SQLException;

    void insert(T model) throws SQLException;

    void update(int id, T model) throws SQLException;

    void delete(int id) throws SQLException;
}
