package com.travelagency.model;

/**
 * Enumeration para tipos de viagem disponíveis
 * Requisito: Tipos Genéricos e Enumeration
 */
public enum TipoViagem {
    NACIONAL("Viagem Nacional"),
    INTERNACIONAL("Viagem Internacional"),
    TURISMO_RELIGIOSO("Turismo Religioso"),
    TURISMO_AVENTURA("Turismo de Aventura"),
    TURISMO_PRAIA("Turismo de Praia"),
    TURISMO_CULTURAL("Turismo Cultural"),
    TURISMO_NEGOCIOS("Turismo de Negócios");

    private final String descricao;

    TipoViagem(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
