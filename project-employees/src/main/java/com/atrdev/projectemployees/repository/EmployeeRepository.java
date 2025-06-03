package com.atrdev.projectemployees.repository;

import com.atrdev.projectemployees.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
