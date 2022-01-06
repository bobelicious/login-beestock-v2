package br.com.serratec.beestock.exceptions;

public class InsufficientQuantityException extends Exception {

    public InsufficientQuantityException(String message) {
        super(message);
    }
    
}
