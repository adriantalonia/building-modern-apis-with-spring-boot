package com.atrdev.todoproject.service;

import com.atrdev.todoproject.model.dto.request.TodoRequest;
import com.atrdev.todoproject.model.dto.response.TodoResponse;
import com.atrdev.todoproject.model.entity.Todo;
import com.atrdev.todoproject.model.entity.User;
import com.atrdev.todoproject.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;

    public TodoServiceImpl(TodoRepository todoRepository, FindAuthenticatedUser findAuthenticatedUser) {
        this.todoRepository = todoRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    @Transactional
    public TodoResponse createTodo(TodoRequest todoRequest) {
        User currentUser = findAuthenticatedUser.getAuthenticatedUser();
        Todo todo = new Todo(
                todoRequest.getTitle(),
                todoRequest.getDescription(),
                todoRequest.getPriority(),
                false,
                currentUser
        );// convert request into todoentity
        Todo savedTodo = todoRepository.save(todo);
        TodoResponse todoResponse = new TodoResponse(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getDescription(),
                savedTodo.getPriority(),
                savedTodo.isComplete()
        );// convert savedtodo into a todoresponse
        return todoResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getAllTodos() {
        User currentUser = findAuthenticatedUser.getAuthenticatedUser();
        return todoRepository.findByOwner(currentUser)
                .stream()
                .map(this::convertToTodoResponse)
                .toList();
    }

    @Override
    public TodoResponse toggleTodoComplete(long id) {
        User currentUser = findAuthenticatedUser.getAuthenticatedUser();
        Optional<Todo> todo = todoRepository.findByIdAndOwner(id, currentUser);
        if (todo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
        todo.get().setComplete(!todo.get().isComplete());
        Todo updatedTodo = todoRepository.save(todo.get());
        return convertToTodoResponse(updatedTodo);
    }

    @Override
    public void deleteTodo(long id) {
        User currentUser = findAuthenticatedUser.getAuthenticatedUser();
        Optional<Todo> todo = todoRepository.findByIdAndOwner(id, currentUser);
        if (todo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
        todoRepository.delete(todo.get());
    }

    private TodoResponse convertToTodoResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getPriority(),
                todo.isComplete()
        );
    }
}
