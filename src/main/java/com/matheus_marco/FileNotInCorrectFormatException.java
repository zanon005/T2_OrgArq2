package com.matheus_marco;

public class FileNotInCorrectFormatException extends Exception{

    private static final long serialVersionUID = -6973103648694191248L;

    public FileNotInCorrectFormatException(String message) {
        super(message);
    }
}