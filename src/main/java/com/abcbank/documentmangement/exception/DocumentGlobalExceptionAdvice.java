package com.abcbank.documentmangement.exception;

import com.abcbank.documentmangement.model.CustDocumentDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class DocumentGlobalExceptionAdvice  extends ResponseEntityExceptionHandler  {
    @ExceptionHandler(MultipartException.class)
    void handleMultipartException(MultipartException ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(),"Please select a file");
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Object> handleNoDataFoundException(
            NoDataFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message",ex.getMessage() );
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<Object> handleNoDataFoundException(
            DocumentNotFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message",ex.getMessage() );
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    @ExceptionHandler(NotValidInputTypeException.class)
    public ResponseEntity<Object> handleNotValidInputTypeData(
            NotValidInputTypeException ex, HttpServletResponse response) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message",ex.getMessage() );
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,HttpServletResponse response) throws IOException {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message",ex.getMessage() );
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

    }


}