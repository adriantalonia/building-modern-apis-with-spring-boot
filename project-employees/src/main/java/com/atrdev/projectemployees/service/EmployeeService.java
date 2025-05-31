package com.atrdev.projectemployees.service;

import com.atrdev.projectemployees.model.dto.request.EmployeeRequest;
import com.atrdev.projectemployees.model.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findById(long id);
    Employee save(EmployeeRequest employee);
    Employee update(long id, EmployeeRequest employeeRequest);
    void deleteById(long id);
    Employee convertToEmployee(long id, EmployeeRequest employeeRequest);
}
