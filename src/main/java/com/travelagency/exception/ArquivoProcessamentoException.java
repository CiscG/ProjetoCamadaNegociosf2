package com.travelagency.exception;

/**
 * Exceção não verificada para operações com arquivo
 * Requisito: Tipos de Exceções (tempo de execução e verificadas)
 */
public class ArquivoProcessamentoException extends RuntimeException {
    private String caminhoArquivo;

    public ArquivoProcessamentoException(String message) {
        super(message);
    }

    public ArquivoProcessamentoException(String message, String caminhoArquivo) {
        super(message);
        this.caminhoArquivo = caminhoArquivo;
    }

    public ArquivoProcessamentoException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }
}
