package br.dev.dantas.point.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandleAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultErrorMessage> handleNotFoundException(NotFoundException exception) {

        var erroResponse = new DefaultErrorMessage(HttpStatus.NOT_FOUND.value(), exception.getReason());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);
    }
}
