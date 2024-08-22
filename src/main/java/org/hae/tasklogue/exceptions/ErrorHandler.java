package org.hae.tasklogue.exceptions;

import jakarta.mail.MessagingException;
import org.hae.tasklogue.exceptions.errors.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.hae.tasklogue.utils.enums.ErrorCodes.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@RestController
public class ErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AccountExist.class)
    public ResponseEntity<ApplicationError> handleAccountExist(AccountExist exist) {
        return ResponseEntity.status(UNAUTHORIZED).body(
                ApplicationError.builder()
                        .code(ACCOUNT_EXIST.getCode())
                        .description(ACCOUNT_EXIST.getDescription())
                        .message(exist.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(EmptyRequiredFields.class)
    public ResponseEntity<ApplicationError> handleEmptyRequiredFields(EmptyRequiredFields emptyRequiredFields) {
        return ResponseEntity.status(UNAUTHORIZED).body(
                ApplicationError.builder()
                        .code(EMPTY_REQUIRED_FIELDS.getCode())
                        .description(EMPTY_REQUIRED_FIELDS.getDescription())
                        .message(emptyRequiredFields.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ActivationCodeExpired.class)
    public ResponseEntity<ApplicationError> handleActivationCodeExpired(ActivationCodeExpired activationCodeExpired) {
        return ResponseEntity.status(UNAUTHORIZED).body(
                ApplicationError.builder()
                        .code(ACTIVATION_CODE_EXPIRED.getCode())
                        .description(ACTIVATION_CODE_EXPIRED.getDescription())
                        .message(activationCodeExpired.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ApplicationError> handleAccountLockedException(LockedException lockedException) {


        return ResponseEntity.status(UNAUTHORIZED).body(
                ApplicationError.builder()
                        .code(ACCOUNT_LOCKED.getCode())
                        .description(ACCOUNT_LOCKED.getDescription())
                        .message(lockedException.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(AccountDisabled.class)
    public ResponseEntity<ApplicationError> handleAccountDisabledException(AccountDisabled accountDisabled) {


        return ResponseEntity.status(UNAUTHORIZED).body(
                ApplicationError.builder()
                        .code(ACCOUNT_DISABLED.getCode())
                        .description(ACCOUNT_DISABLED.getDescription())
                        .message(accountDisabled.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ApplicationError> handleMessagingException(MessagingException exception) {


        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ApplicationError.builder()
                        .message(exception.getMessage())
                        .build()
        );
    }
}
