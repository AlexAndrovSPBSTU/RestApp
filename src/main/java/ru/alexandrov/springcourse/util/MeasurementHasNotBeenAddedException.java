package ru.alexandrov.springcourse.util;

public class MeasurementHasNotBeenAddedException extends RuntimeException {
    public MeasurementHasNotBeenAddedException(String s) {
        super(s);
    }
}
