package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.sber.domain.Employee;
import ru.sber.domain.enums.Position;
import ru.sber.exception.IdAlreadyExistsException;
import ru.sber.repository.EmployeeRepository;
import ru.sber.repository.implementations.EmployeeRepositoryImpl;

@Controller
@EnableAspectJAutoProxy
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public String getEmployeesPage(Model model) {
        model.addAttribute("employees", repository.fetchAll());
        return "employees";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable int id) {
        repository.delete(id);
        return "redirect:/employee/all";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(@PathVariable int id, Model model) {
        model.addAttribute("employee", repository.getById(id));
        return "update-employee";
    }

    @PutMapping("/update/{id}")
    public String updateEmployee(@PathVariable int id,
                                 @ModelAttribute Employee employee) throws IdAlreadyExistsException {
        repository.update(id, employee);
        return "redirect:/employee/all";
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee) throws IdAlreadyExistsException {
        repository.create(employee);
        return "redirect:/employee/all";
    }

    @ExceptionHandler(IdAlreadyExistsException.class)
    public String handleIdAlreadyExistsException(IdAlreadyExistsException ex, Model model) {
        model.addAttribute("message", "id не уникален, ошибка.");
        model.addAttribute("employees", repository.fetchAll());
        return "employees";
    }
}
