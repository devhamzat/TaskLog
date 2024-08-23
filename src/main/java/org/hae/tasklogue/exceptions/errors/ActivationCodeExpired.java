package org.hae.tasklogue.exceptions.errors;

public class ActivationCodeExpired extends RuntimeException{
    public ActivationCodeExpired(String error) {
        super(error);
    }

}
