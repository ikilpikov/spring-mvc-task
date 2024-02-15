package ru.sber.repository;

import ru.sber.domain.Employee;
import ru.sber.exception.IdAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
        void create(Employee employee) throws IdAlreadyExistsException;

        Optional<Employee> getById(int id);

        List<Employee> fetchAll();

        void update(int id, Employee employee);

        void delete(int id);

}
