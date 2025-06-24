package com.atrdev.todoproject.service;

import com.atrdev.todoproject.model.dto.request.TodoRequest;
import com.atrdev.todoproject.model.dto.response.TodoResponse;

import java.util.List;

public interface TodoService {
    TodoResponse createTodo(TodoRequest todoRequest);
    List<TodoResponse> getAllTodos();
}
