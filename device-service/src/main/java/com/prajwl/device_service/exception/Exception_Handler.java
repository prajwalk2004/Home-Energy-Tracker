package com.prajwl.device_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@RestControllerAdvice
public class Exception_Handler {
    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<String>handelNotFoundException(DeviceNotFoundException deviceNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
               .body(deviceNotFoundException.getMessage());
    }

}
