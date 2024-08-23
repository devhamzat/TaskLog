package org.hae.tasklogue.exceptions.errors;

public class AccountDisabled extends RuntimeException {
    public AccountDisabled(String error) {
        super(error);
    }
}
