package com.TryingThingsOut.handlerservice.utils;

import com.TryingThingsOut.handlerservice.exceptions.NoSuchTranslatorException;
import com.TryingThingsOut.handlerservice.models.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.Map;

@Slf4j
public class ExceptionHandler {
    private static final Map<Class, HttpStatus> exceptionToStatus = Map.of(
            NoSuchTranslatorException.class, HttpStatus.NOT_FOUND,
            Exception.class, HttpStatus.INTERNAL_SERVER_ERROR
    );

    private static HttpStatus getStatusFromException(Exception e) {
        HttpStatus status = exceptionToStatus.get(e.getClass());
        return status == null ? HttpStatus.INTERNAL_SERVER_ERROR : status;
    }

    public ResponseEntity handleException(Exception exception) {
        HttpStatus status = getStatusFromException(exception);
        return ResponseEntity.status(status.value()).body(ErrorMessage.builder()
                .errorCode(String.valueOf(status.value()))
                .errorMessage(exception.getMessage())
                .timestamp(new Timestamp(System.currentTimeMillis()).toString())
                .build());
    }
}
