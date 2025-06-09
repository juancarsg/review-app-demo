package com.juancarsg.reviews.backend.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomExceptionDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                                    HttpServletRequest httpServletRequest) {
        CustomExceptionDto customExceptionDto = new CustomExceptionDto();
        customExceptionDto.setUrl(httpServletRequest.getRequestURI());
        customExceptionDto.setType(ex.getClass().getSimpleName());
        if (ex.getCause() instanceof ConstraintViolationException cve
                && cve.getKind() == ConstraintViolationException.ConstraintKind.UNIQUE) {
            customExceptionDto.setMessage("Duplicate entry for this request.");
        } else {
            customExceptionDto.setMessage(ex.getMessage());
        }
        customExceptionDto.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(customExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomExceptionDto> handleEntityNotFoundException(EntityNotFoundException ex,
                                                                            HttpServletRequest httpServletRequest) {
        CustomExceptionDto customExceptionDto = new CustomExceptionDto();
        customExceptionDto.setUrl(httpServletRequest.getRequestURI());
        customExceptionDto.setType(ex.getClass().getSimpleName());
        customExceptionDto.setMessage(ex.getMessage());
        customExceptionDto.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(customExceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionDto> handleEntityCustomException(CustomException ex,
                                                                          HttpServletRequest httpServletRequest) {
        CustomExceptionDto customExceptionDto = new CustomExceptionDto();
        customExceptionDto.setUrl(httpServletRequest.getRequestURI());
        customExceptionDto.setType(ex.getClass().getSimpleName());
        customExceptionDto.setMessage(ex.getMessage());
        customExceptionDto.setStatus(ex.getStatus());
        return new ResponseEntity<>(customExceptionDto, HttpStatus.valueOf(ex.getStatus()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomExceptionDto> handleAccessDeniedException(AccessDeniedException ex,
                                                                          HttpServletRequest httpServletRequest) {
        CustomExceptionDto customExceptionDto = new CustomExceptionDto();
        customExceptionDto.setUrl(httpServletRequest.getRequestURI());
        customExceptionDto.setType(ex.getClass().getSimpleName());
        customExceptionDto.setMessage(ex.getMessage());
        customExceptionDto.setStatus(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(customExceptionDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<CustomExceptionDto> handlDataAccessResourceFailureException(DataAccessResourceFailureException ex,
                                                                                      HttpServletRequest httpServletRequest) {
        CustomExceptionDto customExceptionDto = new CustomExceptionDto();
        customExceptionDto.setUrl(httpServletRequest.getRequestURI());
        customExceptionDto.setType(ex.getClass().getSimpleName());
        customExceptionDto.setMessage(ex.getMessage());
        customExceptionDto.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        return new ResponseEntity<>(customExceptionDto, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CustomExceptionDto> handleDataAccessResourceFailureException(MissingServletRequestParameterException ex,
                                                                                       HttpServletRequest httpServletRequest) {
        CustomExceptionDto customExceptionDto = new CustomExceptionDto();
        customExceptionDto.setUrl(httpServletRequest.getRequestURI());
        customExceptionDto.setType(ex.getClass().getSimpleName());
        customExceptionDto.setMessage(ex.getMessage());
        customExceptionDto.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(customExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionDto> handleMethodArgumentNotValidExceptionException(MethodArgumentNotValidException ex,
                                                                                             HttpServletRequest httpServletRequest) {
        CustomExceptionDto customExceptionDto = new CustomExceptionDto();
        customExceptionDto.setUrl(httpServletRequest.getRequestURI());
        customExceptionDto.setType(ex.getClass().getSimpleName());
        customExceptionDto.setStatus(HttpStatus.BAD_REQUEST.value());
        if (!ex.getBindingResult().getFieldErrors().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Errors encountered: ");
            for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
                sb.append("field " + fe.getField() + " " + fe.getDefaultMessage());
                if (ex.getBindingResult().getFieldErrors().getLast() == fe) {
                    sb.append(".");
                } else {
                    sb.append(", ");
                }
            }
            customExceptionDto.setMessage(sb.toString());
        }
        return new ResponseEntity<>(customExceptionDto, HttpStatus.BAD_REQUEST);
    }

}
