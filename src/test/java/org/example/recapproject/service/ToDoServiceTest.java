package org.example.recapproject.service;

import org.example.recapproject.DTO.ToDoDTO;
import org.example.recapproject.model.STATUS;
import org.example.recapproject.model.ToDo;
import org.example.recapproject.repository.ToDoRepository;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToDoServiceTest {
    private final ToDoRepository toDoRepository = mock(ToDoRepository.class);
    private final ToDoService toDoService = new ToDoService(toDoRepository);

    @Test
    void getAllToDo_shouldReturnNull_IfEmpty() {
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
    void addNewToDo_shouldAddToDo(){

        ToDoDTO toDoDTO = new ToDoDTO("new task", STATUS.OPEN);
        String generatedId = "some-generated-id-123";
        ToDo expectedToDoAfterSave = new ToDo(generatedId, toDoDTO.description(), toDoDTO.status());

        when(toDoRepository.save(any(ToDo.class))).thenReturn(expectedToDoAfterSave);

        ToDo actualToDo = toDoService.addToDo(toDoDTO);

        assertNotNull(actualToDo);
        assertEquals(expectedToDoAfterSave.id(), actualToDo.id());
        assertEquals(expectedToDoAfterSave.description(), actualToDo.description());
        assertEquals(expectedToDoAfterSave.status(), actualToDo.status());

        verify(toDoRepository).save(any(ToDo.class));

    }

    @Test
    void findToDoById_shouldFindToDoById() {
        //Give
        ToDoDTO toDoDTO = new ToDoDTO("new task", STATUS.OPEN);
        String generatedId = "some-generated-id-123";
        ToDo expectedToDo = new ToDo(generatedId, toDoDTO.description(), toDoDTO.status());
        //When
        when(toDoRepository.findById(generatedId)).thenReturn(Optional.of(expectedToDo));

        Optional<ToDo> actualToDoOptional = Optional.ofNullable(toDoService.getToDoById(generatedId));
        ToDo actualToDo = actualToDoOptional.get();
        //Then
         assertTrue(actualToDoOptional.isPresent(), "ToDo should be found");
         assertNotNull(actualToDo);
         assertEquals(expectedToDo.id(), actualToDo.id());
         assertEquals(expectedToDo.description(), actualToDo.description());
         assertEquals(expectedToDo.status(), actualToDo.status());

        verify(toDoRepository, times(1)).findById(generatedId);
    }


    @Test
    void findToDoById_shouldReturnEmptyOptional_IfNotFound() {
        // GIVEN
        String nonExistentId = "non-existent-id";

        when(toDoRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // WHEN
        Optional<ToDo> actualToDoOptional = Optional.ofNullable(toDoService.getToDoById(nonExistentId));

        // THEN
        assertFalse(actualToDoOptional.isPresent(), "ToDo should not be found");
        assertTrue(actualToDoOptional.isEmpty(), "Optional should be empty");


        verify(toDoRepository, times(1)).findById(nonExistentId);
        verifyNoMoreInteractions(toDoRepository);
    }

    @Test
    void updateToDoById_shouldUpdateToDo() {
        //Give

        ToDoDTO toDoDTO = new ToDoDTO("old task", STATUS.OPEN);
        String generatedId = "some-generated-id-123";
        ToDo oldToDo = new ToDo(generatedId, toDoDTO.description(), toDoDTO.status());
        ToDo expectedToDo = new ToDo(generatedId, "new task", STATUS.DOING);
        //When
        when(toDoRepository.findById(generatedId)).thenReturn(Optional.of(expectedToDo));
        doNothing().when(toDoRepository).delete(oldToDo);
        when(toDoRepository.save(expectedToDo)).thenReturn(expectedToDo);

        Optional<ToDo> actualToDo = toDoRepository.findById(generatedId);

        //Then
        assertTrue(actualToDo.isPresent());
        assertEquals(expectedToDo.description(), actualToDo.get().description());
        assertEquals(expectedToDo.status(), actualToDo.get().status());
        verify(toDoRepository, times(1)).findById(generatedId);
        verifyNoMoreInteractions(toDoRepository);
    }

}