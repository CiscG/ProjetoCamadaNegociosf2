package com.airbnbclone.dto;

public record HospedeResponse(
        String id,
        String nome,
        String email,
        String tipo,
        String dataCadastro
) {}
