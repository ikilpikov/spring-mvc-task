package ru.sber.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.domain.enums.Department;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Director {
    private int id;
    private String name;
    private Department department;
    private List<Integer> employees;

}
