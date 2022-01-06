package br.com.serratec.beestock.exceptions;

public class CompletedOrderException extends Exception {

    public CompletedOrderException(String message) {
        super(message);
    }
}
