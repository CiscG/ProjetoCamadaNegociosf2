package com.travelagency.exception;

/**
 * Exceção verificada para operações de reserva
 * Requisito: Tipos de Exceções (tempo de execução e verificadas)
 */
public class ReservaException extends Exception {
    private String reservaId;
    private String motivo;

    public ReservaException(String message) {
        super(message);
    }

    public ReservaException(String message, String motivo) {
        super(message);
        this.motivo = motivo;
    }

    public ReservaException(String message, String reservaId, String motivo) {
        super(message);
        this.reservaId = reservaId;
        this.motivo = motivo;
    }

    public ReservaException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getReservaId() {
        return reservaId;
    }

    public String getMotivo() {
        return motivo;
    }
}
