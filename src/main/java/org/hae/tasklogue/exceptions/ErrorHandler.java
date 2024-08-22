package org.hae.tasklogue.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AccountExist.class)
    public ResponseEntity<ApplicationError> handleAccountExist(AccountExist exist) {
        ApplicationError error = new ApplicationError();
        error.setCode(409);
        error.setMessage(exist.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyRequiredFields.class)
    public ResponseEntity<ApplicationError> handleEmptyRequiredFields(EmptyRequiredFields emptyRequiredFields) {
        ApplicationError error = new ApplicationError();
        error.setCode(422);
        error.setMessage(emptyRequiredFields.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ActivationCodeExpired.class)
    public ResponseEntity<ApplicationError> handleActivationCodeExpired(ActivationCodeExpired activationCodeExpired) {
        ApplicationError error = new ApplicationError();
        error.setCode(410);
        error.setMessage(activationCodeExpired.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
