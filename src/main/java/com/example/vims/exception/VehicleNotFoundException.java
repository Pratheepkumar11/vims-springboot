package com.example.vims.exception;

public class VehicleNotFoundException extends RuntimeException {
    String message;

    public VehicleNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
