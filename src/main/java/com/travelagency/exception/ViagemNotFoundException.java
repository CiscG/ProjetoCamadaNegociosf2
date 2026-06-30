package com.travelagency.exception;

/**
 * Exceção verificada (Checked Exception) - Tempo de Execução
 * Requisito: Tipos de Exceções (tempo de execução e verificadas)
 * Requisito: Criação e Lançamento de exceções
 */
public class ViagemNotFoundException extends Exception {
    private String viagemId;

    public ViagemNotFoundException(String message) {
        super(message);
    }

    public ViagemNotFoundException(String message, String viagemId) {
        super(message);
        this.viagemId = viagemId;
    }

    public ViagemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getViagemId() {
        return viagemId;
    }
}
