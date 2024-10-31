package org.hae.tasklogue.utils.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.*;

public enum ErrorCodes {
    NO_CODE(0, NOT_IMPLEMENTED, "NO CODE"),
    INCORRECT_PASSWORD(300, BAD_REQUEST, "PASSWORD IS INCORRECT"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "NEW PASSWORD DOES NOT MATCH"),
    ACCOUNT_LOCKED(302, FORBIDDEN, "ACCOUNT_LOCKED"),
    ACCOUNT_DISABLED(303, FORBIDDEN, "ACCOUNT DISABLED"),
    BAD_CREDENTIALS(304, FORBIDDEN, "USERNAME AND/OR PASSWORD IS INCORRECT"),
    ACCOUNT_EXIST(409, BAD_REQUEST, "ACCOUNT ALREADY EXISTS"),
    ACTIVATION_CODE_EXPIRED(410, BAD_REQUEST, "ACTIVATION CODE EXPIRED"),
    EMPTY_REQUIRED_FIELDS(422, BAD_REQUEST, "A REQUIRED FIELD IS EMPTY"),
    FORBIDDEN_REQUEST(403, FORBIDDEN, "Forbidden ");


    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    ErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
