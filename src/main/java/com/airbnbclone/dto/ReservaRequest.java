package com.airbnbclone.dto;

public record ReservaRequest(
        String propriedadeId,
        String hospedeId,
        String checkin,
        String checkout
) {}
