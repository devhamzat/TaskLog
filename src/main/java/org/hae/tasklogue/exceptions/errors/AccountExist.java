package org.hae.tasklogue.exceptions.errors;

public class AccountExist extends RuntimeException {
    public AccountExist(String error) {
        super(error);
    }
}
