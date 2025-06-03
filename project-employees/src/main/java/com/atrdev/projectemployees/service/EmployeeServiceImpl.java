package com.atrdev.projectemployees.service;

import com.atrdev.projectemployees.dao.EmployeeDAO;
import com.atrdev.projectemployees.model.dto.request.EmployeeRequest;
import com.atrdev.projectemployees.model.entity.Employee;
import com.atrdev.projectemployees.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO, EmployeeRepository employeeRepository) {
        this.employeeDAO = employeeDAO;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        //return employeeDAO.findAll();
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(long id) {
        //Employee employee = employeeDAO.findById(id);
        //return employee;
        return employeeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Employee not found for id : " + id));
    }

    @Transactional
    @Override
    public Employee save(EmployeeRequest employeeRequest) {
        Employee employeeEntity = convertToEmployee(0, employeeRequest);
        //return employeeDAO.save(employeeEntity);
        return employeeRepository.save(employeeEntity);
    }

    @Transactional
    @Override
    public Employee update(long id, EmployeeRequest employeeRequest) {
        Employee employeeEntity = convertToEmployee(id, employeeRequest);
        //return employeeDAO.save(employeeEntity);
        return employeeRepository.save(employeeEntity);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        //employeeDAO.deleteById(id);
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee convertToEmployee(long id, EmployeeRequest employeeRequest) {
        return new Employee(id,
                employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getEmail()
        );
    }
}
