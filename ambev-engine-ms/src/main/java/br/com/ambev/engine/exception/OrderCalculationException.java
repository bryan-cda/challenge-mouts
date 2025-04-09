package br.com.ambev.engine.exception;

public class OrderCalculationException extends RuntimeException {
    public OrderCalculationException(String message) {
        super(message);
    }
}
