package org.example.recapproject.service;

import org.example.recapproject.DTO.ToDoDTO;
import org.example.recapproject.model.ToDo;
import org.example.recapproject.repository.ToDoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ToDoService {

private final ToDoRepository toDoRepository;
public ToDoService(ToDoRepository toDoRepository) {
    this.toDoRepository = toDoRepository;
}

public List<ToDo> findAll() {
    return toDoRepository.findAll();
}

    public ToDo addToDo(ToDoDTO toDoDTO) {
    String id = UUID.randomUUID().toString();
    ToDo newToDo = new ToDo(id, toDoDTO.description(), toDoDTO.status());
    return toDoRepository.save(newToDo);
    }
}
