package org.hae.tasklogue.exceptions.errors;

public class ForbiddenRequest extends RuntimeException {
    public ForbiddenRequest(String error) {

        super(error);

    }

}
