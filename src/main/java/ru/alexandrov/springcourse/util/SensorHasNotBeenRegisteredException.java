package ru.alexandrov.springcourse.util;

public class SensorHasNotBeenRegisteredException extends RuntimeException{
    public SensorHasNotBeenRegisteredException(String message) {
        super(message);
    }
}
