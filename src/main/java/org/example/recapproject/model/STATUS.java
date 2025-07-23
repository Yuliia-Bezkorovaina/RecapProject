package org.example.recapproject.model;

public enum STATUS {
    TODO ("ToDo"),
    OPEN ("Open"),
    DOING ("Doing"),
    DONE ("Done");

    private final String displayName;


    STATUS(String displayName) {
        this.displayName = displayName;
    }

   public String getDisplayName() {
        return displayName;
    }

}

