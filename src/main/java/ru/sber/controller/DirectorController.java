package ru.sber.controller;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.domain.Director;
import ru.sber.exception.IdAlreadyExistsException;
import ru.sber.repository.DirectorRepository;
import ru.sber.repository.EmployeeRepository;

import java.util.List;

@Controller
@EnableAspectJAutoProxy
@RequestMapping("/director")
public class DirectorController {

    private DirectorRepository directorRepository;
    private EmployeeRepository employeeRepository;

    public DirectorController(DirectorRepository directorRepository, EmployeeRepository employeeRepository) {
        this.directorRepository = directorRepository;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/all")
    public String getDirectorsPage(Model model) {
        model.addAttribute("directors", directorRepository.fetchAll());
        return "directors";
    }

    @PostMapping("/delete/{id}")
    public String deleteDirector(@PathVariable int id) {
        directorRepository.delete(id);
        return "redirect:/director/all";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(@PathVariable int id, Model model) {
        model.addAttribute("director", directorRepository.getById(id));

        var enabledEmployees = employeeRepository.fetchAll();
        model.addAttribute("employees", enabledEmployees);
        return "update-director";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable int id,
                                 @ModelAttribute Director director,
                                 @RequestParam(name = "employee-id", required = false) List<Integer> selectedEmployees)
            throws IdAlreadyExistsException {

        director.setEmployees(selectedEmployees);
        directorRepository.update(id, director);

        return "redirect:/director/all";
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("director", new Director());
        model.addAttribute("employees", employeeRepository.fetchAll());
        return "add-director";
    }

    @PostMapping("/add")
    public String addDirector(@ModelAttribute Director director,
                              @RequestParam(name = "employee-id", required = false) List<Integer> selectedEmployees)
            throws IdAlreadyExistsException {
        director.setEmployees(selectedEmployees);
        directorRepository.create(director);
        return "redirect:/director/all";
    }

    @ExceptionHandler(IdAlreadyExistsException.class)
    public String handleIdAlreadyExistsException(IdAlreadyExistsException ex, Model model) {
        model.addAttribute("message", "id не уникален, ошибка.");
        model.addAttribute("directors", directorRepository.fetchAll());
        return "directors";
    }

}
