package com.haulmont.testtask.exceptions;

public class MedicamentsSystemException extends RuntimeException {

    private static final String SYSTEM_ERROR_MESSAGE = "Системная ошибка! Обратитесь к администратору.";

    public MedicamentsSystemException() {
        super(SYSTEM_ERROR_MESSAGE);
    }
}
