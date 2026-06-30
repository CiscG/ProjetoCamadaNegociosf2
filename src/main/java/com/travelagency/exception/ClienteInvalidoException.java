package com.travelagency.exception;

/**
 * Exceção não verificada (Unchecked Exception) - Runtime
 * Requisito: Tipos de Exceções (tempo de execução e verificadas)
 */
public class ClienteInvalidoException extends RuntimeException {
    private String clienteId;

    public ClienteInvalidoException(String message) {
        super(message);
    }

    public ClienteInvalidoException(String message, String clienteId) {
        super(message);
        this.clienteId = clienteId;
    }

    public ClienteInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getClienteId() {
        return clienteId;
    }
}
