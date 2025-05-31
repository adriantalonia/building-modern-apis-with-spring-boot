package com.atrdev.projectemployees.dao;

import com.atrdev.projectemployees.model.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

    private final EntityManager entityManager;

    public EmployeeDAOJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {

        // Create query
        TypedQuery<Employee> query = this.entityManager.createQuery("from Employee", Employee.class);

        // execute query and get a result list
        List<Employee> employees = query.getResultList();

        // return results
        return employees;
    }

    @Override
    public Employee findById(long id) {
        Employee employee = this.entityManager.find(Employee.class, id);
        return employee;
    }

    @Override
    public Employee save(Employee employee) {
        Employee savedEmployee = this.entityManager.merge(employee);
        return savedEmployee;
    }

    @Override
    public void deleteById(long id) {
        Employee employee = this.entityManager.find(Employee.class, id);
        this.entityManager.remove(employee);
    }
}
