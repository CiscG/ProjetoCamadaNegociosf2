package com.airbnbclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReservaRequest(
        @JsonProperty("propriedadeId")
        String propriedadeId,
        @JsonProperty("hospedeId")
        String hospedeId,
        String checkin,
        String checkout
) {}
