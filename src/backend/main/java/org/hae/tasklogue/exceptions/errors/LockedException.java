package org.hae.tasklogue.exceptions.errors;

public class LockedException extends RuntimeException{
    public LockedException(String error) {
        super(error);
    }
}
