package com.iterbio.dscommerce.controllers.exceptions;

import com.iterbio.dscommerce.DTO.ErrorResponseDTO;
import com.iterbio.dscommerce.DTO.ValidationErrorDTO;
import com.iterbio.dscommerce.services.exceptions.BodyRequestException;
import com.iterbio.dscommerce.services.exceptions.DataBaseException;
import com.iterbio.dscommerce.services.exceptions.ForbiddenException;
import com.iterbio.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){

        String error = "Not found";
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponseDTO errorBody = ErrorResponseDTO.builder()
                .error(error)
                .status(status.value())
                .timestamp(Instant.now())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(errorBody);

    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ErrorResponseDTO> dataDataBaseException(DataBaseException e, HttpServletRequest request){

        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponseDTO errorBody = ErrorResponseDTO.builder()
                .error(error)
                .status(status.value())
                .timestamp(Instant.now())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(errorBody);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){

        String error = "Invalid entity";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationErrorDTO errorBody = new ValidationErrorDTO(Instant.now(),status.value(),error,"Invalid entity", request.getRequestURI());

        for(FieldError f : e.getFieldErrors()){

            errorBody.addFieldMessage(f.getField(),f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(errorBody);

    }

    @ExceptionHandler(BodyRequestException.class)
    public ResponseEntity<ErrorResponseDTO> bodyRequestException(BodyRequestException e, HttpServletRequest request){

        String error = "Invalid entity";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ErrorResponseDTO errorBody = ErrorResponseDTO.builder()
                .error(error)
                .status(status.value())
                .timestamp(Instant.now())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(errorBody);

    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseDTO> forbidden(ForbiddenException e, HttpServletRequest request){

        String error = "Forbidden";
        HttpStatus status = HttpStatus.FORBIDDEN;

        ErrorResponseDTO errorBody = ErrorResponseDTO.builder()
                .error(error)
                .status(status.value())
                .timestamp(Instant.now())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(errorBody);

    }
}
