package com.example.vims.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {VehicleExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class VehicleExceptionHandlerTest {
    @MockBean
    private Environment environment;

    @Autowired
    private VehicleExceptionHandler vehicleExceptionHandler;


    @Test
    void testHandleEmployeeNotFoundException() {
        when(environment.getProperty(Mockito.<String>any())).thenReturn("Property");
        ResponseEntity<ErrorInfo> actualHandleEmployeeNotFoundExceptionResult = vehicleExceptionHandler
                .handleVehicleNotFoundException(new VehicleNotFoundException("An error occurred"));
        assertTrue(actualHandleEmployeeNotFoundExceptionResult.hasBody());
        assertTrue(actualHandleEmployeeNotFoundExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleEmployeeNotFoundExceptionResult.getStatusCode());
        ErrorInfo body = actualHandleEmployeeNotFoundExceptionResult.getBody();
        assertEquals(404, body.getErrorCode().intValue());
        assertEquals("Property", body.getErrorMessage());
        verify(environment).getProperty(Mockito.<String>any());
    }

    @Test
    void testExceptionHandler3() {
        ResponseEntity<ErrorInfo> actualExceptionHandlerResult = vehicleExceptionHandler
                .exceptionHandler(new MethodArgumentNotValidException(null, new BindException("Target", "Object Name")));
        assertTrue(actualExceptionHandlerResult.hasBody());
        assertTrue(actualExceptionHandlerResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualExceptionHandlerResult.getStatusCode());
        ErrorInfo body = actualExceptionHandlerResult.getBody();
        assertEquals(400, body.getErrorCode().intValue());
        assertEquals("", body.getErrorMessage());
    }

    @Test
    void testConstraintViolationExceptionHandler() {
        ResponseEntity<ErrorInfo> actualExceptionHandlerResult = vehicleExceptionHandler
                .exceptionHandler(new ConstraintViolationException(new HashSet<>()));
        assertTrue(actualExceptionHandlerResult.hasBody());
        assertTrue(actualExceptionHandlerResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualExceptionHandlerResult.getStatusCode());
        ErrorInfo body = actualExceptionHandlerResult.getBody();
        assertEquals(400, body.getErrorCode().intValue());
        assertEquals("", body.getErrorMessage());
    }
}