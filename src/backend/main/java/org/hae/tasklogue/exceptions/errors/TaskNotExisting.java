package org.hae.tasklogue.exceptions.errors;

public class TaskNotExisting extends RuntimeException{
    public TaskNotExisting(String error) {

        super(error);

    }
}
