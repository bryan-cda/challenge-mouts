package br.com.ambev.engine.exception;

public class DeleteProductException extends RuntimeException {
    public DeleteProductException(String message) {
        super(message);
    }
}
