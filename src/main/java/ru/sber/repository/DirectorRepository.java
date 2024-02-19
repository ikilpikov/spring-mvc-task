package ru.sber.repository;

import org.springframework.aop.target.LazyInitTargetSource;
import ru.sber.domain.Director;
import ru.sber.exception.IdAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface DirectorRepository {
    void create(Director director) throws IdAlreadyExistsException;

    Optional<Director> getById(int id);

    List<Director> fetchAll();

    void update(int id, Director director) throws IdAlreadyExistsException;

    void delete(int id);

}
