package ru.sber.repository.implementations;

import org.springframework.stereotype.Repository;
import ru.sber.domain.Director;
import ru.sber.domain.enums.Department;
import ru.sber.exception.IdAlreadyExistsException;
import ru.sber.repository.DirectorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DirectorRepositoryImpl implements DirectorRepository {
    private static final List<Director> DIRECTORS = new ArrayList<>();
    private static int ID = 0;

    static {
        DIRECTORS.add(new Director(ID++, "Valera", Department.ANALYSIS, new ArrayList<>(List.of(0, 1, 2))));
        DIRECTORS.add(new Director(ID++, "Semen", Department.DEVELOPMENT, new ArrayList<>(List.of(1, 2))));
    }

    @Override
    public void create(Director director) throws IdAlreadyExistsException {
        if (getById(director.getId()).isPresent()) {
            throw new IdAlreadyExistsException("Director with id " + director.getId() + " exists");
        }

        DIRECTORS.add(director);
    }

    @Override
    public Optional<Director> getById(int id) {
        return DIRECTORS.stream().filter(emp -> emp.getId() == id).findFirst();
    }

    @Override
    public List<Director> fetchAll() {
        return new ArrayList<>(DIRECTORS);
    }

    @Override
    public void update(int id, Director director) {
        DIRECTORS.stream()
                .filter(dir -> dir.getId() == id)
                .forEach(dir -> {
                    dir.setName(director.getName());
                    dir.setDepartment(dir.getDepartment());
                    dir.setEmployees(director.getEmployees());
                });
    }

    @Override
    public void delete(int id) {
        DIRECTORS.removeIf(dir -> dir.getId() == id);
    }

}
