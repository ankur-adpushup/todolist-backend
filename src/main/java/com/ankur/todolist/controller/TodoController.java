package com.ankur.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ankur.todolist.service.TodoService;
import com.ankur.todolist.dto.TodoDto;
import com.ankur.todolist.entity.Todo;
import com.ankur.todolist.exception.EntityNotFoundException;

import java.util.*;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping(path = "/test")
    public ResponseEntity<String> testController() {
        String testString = "This controller is working!";
        return new ResponseEntity<>(testString, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable(value = "id") Long todoId) {
        TodoDto todoDto = todoService.getTodoById(todoId);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto) {
        TodoDto todo = todoService.createTodo(todoDto);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @DeleteMapping
    ResponseEntity<String> deleteTodo(@RequestParam Long id) {
        todoService.deleteById(id);
        String res = "Todo with id = " + id + " is deleted!";
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable(value = "id") Long todoId, TodoDto todoDetails) {
        todoService.updateTodo(todoId, todoDetails);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
