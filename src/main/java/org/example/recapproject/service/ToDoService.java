package org.example.recapproject.service;

import org.example.recapproject.model.ToDo;
import org.example.recapproject.repository.ToDoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {

private final ToDoRepository toDoRepository;
public ToDoService(ToDoRepository toDoRepository) {
    this.toDoRepository = toDoRepository;
}

public List<ToDo> findAll() {
    return toDoRepository.findAll();
}
}
