package dao;

import java.util.List;

public interface GenericDAO<T, ID> {
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(T entity);
}
