package org.example.recapproject.DTO;

import lombok.With;
import org.example.recapproject.model.STATUS;
@With
public record ToDoDTO(String description, STATUS status) {
}
