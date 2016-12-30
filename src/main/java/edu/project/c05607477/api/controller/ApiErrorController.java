package edu.project.c05607477.api.controller;

import edu.project.c05607477.api.model.ErrorResponse;
import edu.project.c05607477.exceptions.EmptyAccountException;
import edu.project.c05607477.exceptions.InvalidTransferRequestException;
import edu.project.c05607477.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ApiErrorController implements ErrorController {

    private final static String ERROR_PATH = "/error";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        String errorCode = "404";
        String errorMsg = "Requested user doesn't exist";

        return new ResponseEntity(new ErrorResponse(errorCode, errorMsg), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyAccountException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleEmptyAccountException(EmptyAccountException e) {
        String errorCode = "404";
        String errorMsg = "Requested user have no account";

        return new ResponseEntity(new ErrorResponse(errorCode, errorMsg), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTransferRequestException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleInvalidTransferRequestException(InvalidTransferRequestException e) {
        String errorCode = "400";
        String errorMsg = e.getMessage();

        return new ResponseEntity(new ErrorResponse(errorCode, errorMsg), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleThrowable(Throwable e) {
        logger.error("Unexpected error occurs", e);

        String errorCode = "500";
        String errorMsg = e.getMessage();

        return new ResponseEntity(new ErrorResponse(errorCode, errorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
