package com.ankur.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ankur.todolist.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}