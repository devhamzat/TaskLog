package org.hae.tasklogue.exceptions;

public class ActivationCodeExpired extends RuntimeException{
    public ActivationCodeExpired(String error) {
        super(error);
    }

}
