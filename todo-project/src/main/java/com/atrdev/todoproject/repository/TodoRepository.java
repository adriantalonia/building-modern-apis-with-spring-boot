package com.atrdev.todoproject.repository;

import com.atrdev.todoproject.model.entity.Todo;
import com.atrdev.todoproject.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findByOwner(User owner);
    Optional<Todo> findByIdAndOwner(Long id, User user);
}
