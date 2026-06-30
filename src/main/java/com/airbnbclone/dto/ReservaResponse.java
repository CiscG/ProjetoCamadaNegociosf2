package com.airbnbclone.dto;

public record ReservaResponse(
        String id,
        String propriedadeId,
        String hospedeId,
        String checkin,
        String checkout,
        Double valorTotal,
        String status,
        String dataReserva,
        PropriedadeResponse propriedade
) {}
