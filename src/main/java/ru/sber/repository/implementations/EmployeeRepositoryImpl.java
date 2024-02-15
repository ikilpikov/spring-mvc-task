package ru.sber.repository.implementations;

import org.springframework.stereotype.Repository;
import ru.sber.domain.Employee;
import ru.sber.domain.enums.Position;
import ru.sber.exception.IdAlreadyExistsException;
import ru.sber.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private static final List<Employee> EMPLOYEES = new ArrayList<>();
    private static int ID = 0;

    {
        EMPLOYEES.add(new Employee(ID++, "Vasya", Position.JUNIOR));
        EMPLOYEES.add(new Employee(ID++, "Antoxa", Position.SENIOR));
        EMPLOYEES.add(new Employee(ID++, "Sanya", Position.MIDDLE));
    }

    @Override
    public void create(Employee employee) throws IdAlreadyExistsException {
        if (getById(employee.getId()) != null) {
            throw new IdAlreadyExistsException("Employee with id " + employee.getId() + " exists");
        }
        EMPLOYEES.add(employee);
    }

    @Override
    public Optional<Employee> getById(int id) {
        return EMPLOYEES.stream().filter(emp -> emp.getId() == id).findFirst();
    }

    @Override
    public List<Employee> fetchAll() {
        return new ArrayList<>(EMPLOYEES);
    }

    @Override
    public void update(int id, Employee employee) {
        EMPLOYEES.stream()
                .filter(emp -> emp.getId() == id)
                .forEach(emp -> {
                    emp.setName(employee.getName());
                    emp.setPosition(employee.getPosition());
                });
    }

    @Override
    public void delete(int id) {
        EMPLOYEES.removeIf(emp -> emp.getId() == id);
    }

}
