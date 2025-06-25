package com.atrdev.todoproject.controller;

import com.atrdev.todoproject.model.dto.request.TodoRequest;
import com.atrdev.todoproject.model.dto.response.TodoResponse;
import com.atrdev.todoproject.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/todos")
@Tag(name = "Todo REST AI Endpoints", description = "Operations for managing user todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(summary = "Create todo for user", description = "Create todo for the signed in user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoRequest todoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(todoRequest));
    }

    @Operation(summary = "Get all todos for user", description = "Fetch all todos from signed in user")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getAllTodos());
    }

    @Operation(summary = "Update todo for user", description = "Update todo for the signed in user")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> toggleTodoCompletion(@PathVariable @Min(1) long id) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.toggleTodoComplete(id));
    }

    @Operation(summary = "Delete todo for user", description = "Delete todo for the signed in user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable @Min(1) long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

}
