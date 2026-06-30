package com.travelagency.exception;

/**
 * Exceção verificada para datas inválidas
 * Requisito: Tipos de Exceções (tempo de execução e verificadas)
 */
public class DatasInvalidasException extends Exception {
    private String dataPartida;
    private String dataRetorno;

    public DatasInvalidasException(String message) {
        super(message);
    }

    public DatasInvalidasException(String message, String dataPartida, String dataRetorno) {
        super(message);
        this.dataPartida = dataPartida;
        this.dataRetorno = dataRetorno;
    }

    public DatasInvalidasException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getDataPartida() {
        return dataPartida;
    }

    public String getDataRetorno() {
        return dataRetorno;
    }
}
