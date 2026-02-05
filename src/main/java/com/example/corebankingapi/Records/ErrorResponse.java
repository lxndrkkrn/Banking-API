package com.example.corebankingapi.Records;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime localDateTime,
        int status,
        String error,
        String message
) {}
