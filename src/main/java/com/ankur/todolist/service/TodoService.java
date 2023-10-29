package com.ankur.todolist.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ankur.todolist.entity.Todo;
import com.ankur.todolist.dto.TodoDto;
import com.ankur.todolist.repository.TodoRepository;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoDto getTodoById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("s"));
        TodoDto todoDto = convertToTodoDto(todo);
        return todoDto;
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map(this::convertToTodoDto).collect(Collectors.toList());
    }

    public TodoDto createTodo(TodoDto todoDto) {
        Todo todo = convertToEntity(todoDto);
        todoRepository.save(todo);
        return convertToTodoDto(todo);
    }

    public TodoDto updateTodo(Long todoId, TodoDto todoDetails) {

        TodoDto todoToBeUpdated = this.getTodoById(todoId);
        String newTodoString = todoDetails.getTodoString();
        Boolean newCompletedValue = todoDetails.isCompleted();
        System.out.println("values are : newCompe" + newCompletedValue);
        Long newUserId = todoDetails.getUserId();
        if (newTodoString != null) {
            todoToBeUpdated.setTodoString(newTodoString);
        }
        if (newCompletedValue instanceof Boolean) {
            todoToBeUpdated.setCompleted(newCompletedValue);
        }
        if (newUserId != null) {
            todoToBeUpdated.setUserId(newUserId);
        }
        Todo updatedTodo = new Todo();
        BeanUtils.copyProperties(todoToBeUpdated, updatedTodo);
        todoRepository.save(updatedTodo);
        return todoToBeUpdated;
    }

    private TodoDto convertToTodoDto(Todo todo) {
        TodoDto todoDto = new TodoDto();
        BeanUtils.copyProperties(todo, todoDto);
        return todoDto;
    }

    private Todo convertToEntity(TodoDto todoDto) {
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoDto, todo);
        return todo;
    }
}
