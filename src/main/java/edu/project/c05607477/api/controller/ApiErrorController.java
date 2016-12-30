package edu.project.c05607477.api.controller;

import edu.project.c05607477.api.model.ErrorResponse;
import edu.project.c05607477.exceptions.EmptyAccountException;
import edu.project.c05607477.exceptions.UserNotFoundException;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiErrorController implements ErrorController {

    private final static String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        String errorCode = "404";
        String errorMsg = "Requested user doesn't exist";

        return new ResponseEntity(new ErrorResponse(errorCode, errorMsg), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyAccountException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleEmptyAccountException(EmptyAccountException e) {
        String errorCode = "404";
        String errorMsg = "Requested user have no account";

        return new ResponseEntity(new ErrorResponse(errorCode, errorMsg), HttpStatus.NOT_FOUND);
    }

}
