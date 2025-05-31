package com.atrdev.projectemployees.controller;


import com.atrdev.projectemployees.model.dto.request.EmployeeRequest;
import com.atrdev.projectemployees.model.entity.Employee;
import com.atrdev.projectemployees.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee REST API Endpoints", description = "Operations related to employees")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Get all employees", description = "Get all employees")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @Operation(summary = "Get employee by id", description = "Get employee by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable @Min(value = 1) long employeeId) {
        return ResponseEntity.ok(employeeService.findById(employeeId));
    }

    @Operation(summary = "Add employee", description = "Add employee")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(employeeRequest));
    }

    @Operation(summary = "Update employee", description = "Update employee")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable @Min(value = 1) long id, @Valid @RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.update(id, employeeRequest));
    }

    @Operation(summary = "Delete employee by id", description = "Delete employee by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable @Min(value = 1) long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
