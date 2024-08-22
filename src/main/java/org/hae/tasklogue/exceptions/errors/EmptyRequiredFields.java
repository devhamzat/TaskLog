package org.hae.tasklogue.exceptions.errors;

public class EmptyRequiredFields extends RuntimeException{


    public EmptyRequiredFields(String error) {
        super(error);
    }
}
