package com.airbnbclone.dto;

import jakarta.validation.constraints.NotBlank;

public record ReservaRequest(
        @NotBlank(message = "propriedadeId não pode ser vazio")
        String propriedadeId,
        
        @NotBlank(message = "hospedeId não pode ser vazio")
        String hospedeId,
        
        @NotBlank(message = "checkin não pode ser vazio")
        String checkin,
        
        @NotBlank(message = "checkout não pode ser vazio")
        String checkout
) {}
