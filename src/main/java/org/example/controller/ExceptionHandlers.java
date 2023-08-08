package org.example.controller;

import jakarta.validation.ConstraintViolationException;
import org.example.exceptions.BadRequestException;
import org.example.exceptions.IException;
import org.example.exceptions.NotFoundException;
import org.example.model.MessageResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlers {



    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<MessageResponse> notFoundException(NotFoundException ex) {
        HttpStatus status = ex.getHttpStatus() == null ? HttpStatus.NOT_FOUND : ex.getHttpStatus();
        return responseBuilder(ex.getMessage(), status);
    }

    @ExceptionHandler({BadRequestException.class,
            IllegalArgumentException.class})
    @ResponseBody
    public ResponseEntity<MessageResponse> badRequestException(BadRequestException ex) {
        HttpStatus status = ex.getHttpStatus() == null ? HttpStatus.BAD_REQUEST : ex.getHttpStatus();
        return responseBuilder(ex.getMessage(), status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<MessageResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String errorMessage = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return responseBuilder(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<MessageResponse> runtimeException(RuntimeException exp) {
        if (exp instanceof IException) {
            return responseBuilder(exp.getMessage(), ((IException) exp).getHttpStatus());
        }
//        String message = messageHandler.getMessage(MessageConstants.GENERAL_INTERNAL_SERVER_ERROR);
        return responseBuilder("message", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
//            InvalidDataAccessApiUsageException.class,
//            PropertyValueException.class,
            ConstraintViolationException.class
//            DataIntegrityViolationException.class
    })
    @ResponseBody
    public ResponseEntity<MessageResponse> argumentException(Exception ex) {
//        String message = messageHandler.getMessage(MessageConstants.GENERAL_DATA_INVALID_OR_INCONSISTENCY);
        return responseBuilder("message", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<MessageResponse> globalException(Exception ex) {
//        String message = messageHandler.getMessage(MessageConstants.GENERAL_INTERNAL_SERVER_ERROR);
        return responseBuilder("message", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<MessageResponse> responseBuilder(String message, HttpStatus status){
        return new ResponseEntity<MessageResponse>(new MessageResponse(message), status);
    }
}
