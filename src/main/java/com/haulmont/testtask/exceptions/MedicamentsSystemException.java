package com.haulmont.testtask.exceptions;

public class MedicamentsSystemException extends RuntimeException {

    public MedicamentsSystemException(String medicamentsSystemErrorMessage) {
        super(medicamentsSystemErrorMessage);
    }
}
