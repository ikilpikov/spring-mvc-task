package ru.sber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.domain.Director;
import ru.sber.domain.Employee;
import ru.sber.exception.IdAlreadyExistsException;
import ru.sber.repository.DirectorRepository;

@Controller
@EnableAspectJAutoProxy
@RequestMapping("/director")
public class DirectorController {

    private DirectorRepository repository;

    public DirectorController(DirectorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public String getDirectorsPage(Model model) {
        model.addAttribute("directors", repository.fetchAll());
        return "directors";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDirector(@PathVariable int id) {
        repository.delete(id);
        return "redirect:/director/all";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(@PathVariable int id, Model model) {
        model.addAttribute("director", repository.getById(id));
        return "update-director";
    }

    @PutMapping("/update/{id}")
    public String updateEmployee(@PathVariable int id, @ModelAttribute Director director) throws IdAlreadyExistsException {
        repository.update(id, director);
        return "redirect:/director/all";
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("director", new Director());
        return "add-director";
    }

    @PostMapping("/add")
    public String addDirector(@ModelAttribute Director director) throws IdAlreadyExistsException {
        repository.create(director);
        return "redirect:/director/all";
    }

    @ExceptionHandler(IdAlreadyExistsException.class)
    public String handleIdAlreadyExistsException(IdAlreadyExistsException ex, Model model) {
        model.addAttribute("message", "id не уникален, ошибка.");
        model.addAttribute("directors", repository.fetchAll());
        return "directors";
    }

}
