package org.example.recapproject.service;

import org.example.recapproject.DTO.ToDoDTO;
import org.example.recapproject.model.STATUS;
import org.example.recapproject.model.ToDo;
import org.example.recapproject.repository.ToDoRepository;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToDoServiceTest {
    private final ToDoRepository toDoRepository = mock(ToDoRepository.class);
    private final ToDoService toDoService = new ToDoService(toDoRepository);

    @Test
    void getAllToDo_shouldReturnNull_IfEmpty() throws Exception {
        // Given
        when(toDoRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<ToDo> actualToDos = toDoService.findAll();

        // Then

        assertNotNull(actualToDos);
        assertTrue(actualToDos.isEmpty());


        verify(toDoRepository, times(1)).findAll();
    }

    @Test
    void addNewToDo_shouldAddToDo() throws Exception {

        ToDoDTO toDoDTO = new ToDoDTO("new task", STATUS.TODO);
        String generatedId = "some-generated-id-123";
        ToDo expectedToDoAfterSave = new ToDo(generatedId, toDoDTO.description(), toDoDTO.status());

        when(toDoRepository.save(any(ToDo.class))).thenReturn(expectedToDoAfterSave);

        ToDo actualToDo = toDoService.addToDo(toDoDTO);

        assertNotNull(actualToDo);
        assertEquals(expectedToDoAfterSave.id(), actualToDo.id());
        assertEquals(toDoDTO.description(), actualToDo.description());
        assertEquals(toDoDTO.status(), actualToDo.status());

        verify(toDoRepository).save(any(ToDo.class));

    }




}