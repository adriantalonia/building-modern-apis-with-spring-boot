package com.atrdev.projectemployees.dao;

import com.atrdev.projectemployees.model.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> findAll();
    Employee findById(long id);
    Employee save(Employee employee);
    void deleteById(long id);
}
