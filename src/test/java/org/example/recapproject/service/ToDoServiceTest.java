package org.example.recapproject.service;

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
        List<ToDo> actualToDos = toDoService.findAll(); // Assuming this is your service method

        // Then
        // Verify that the returned list is empty
        assertNotNull(actualToDos); // It should return an empty list, not null
        assertTrue(actualToDos.isEmpty());

        // Verify that findAll was called on the repository
        verify(toDoRepository, times(1)).findAll();
    }

}