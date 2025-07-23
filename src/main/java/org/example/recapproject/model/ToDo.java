package org.example.recapproject.model;

import lombok.With;

@With
public record ToDo(String id, String description, STATUS status) {
}
