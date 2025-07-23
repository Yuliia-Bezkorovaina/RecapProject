package org.example.recapproject.controll;

import org.example.recapproject.DTO.ToDoDTO;
import org.example.recapproject.model.ToDo;
import org.example.recapproject.service.ToDoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class ToDoController {
    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public List<ToDo> getAllToDo(){
        return toDoService.findAll();
    }

    @PostMapping
    public ToDo addToDo(@RequestBody ToDoDTO toDoDTO) {
        return toDoService.addToDo(toDoDTO);
    }

    @GetMapping("/{id}")
    public ToDo getToDoById(@PathVariable String id) {
        return toDoService.getToDoById(id);
    }

    @PutMapping("/{id}")
    public ToDo updateToDo(@PathVariable String id, @RequestBody ToDoDTO toDoDTO) {
        return toDoService.updateToDoById(id,  toDoDTO);
    }

}
