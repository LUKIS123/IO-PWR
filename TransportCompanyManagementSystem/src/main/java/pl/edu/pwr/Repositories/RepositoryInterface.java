package pl.edu.pwr.Repositories;

import java.util.List;

public interface RepositoryInterface<T> {
    List<T> getAll();

    T getById(int id);

    void insert(T model);

    void delete(int id);
}
