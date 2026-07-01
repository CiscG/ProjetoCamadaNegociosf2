package com.airbnbclone.dto;

import com.airbnbclone.model.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PropriedadeRequest(
        @JsonProperty("anfitriaoId")
        String anfitriaoId,
        String titulo,
        String descricao,
        @JsonProperty("precoPorNoite")
        Double precoPorNoite,
        Endereco endereco,
        List<String> comodidades
) {}
