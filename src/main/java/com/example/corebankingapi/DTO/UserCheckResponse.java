package com.example.corebankingapi.DTO;

import com.example.corebankingapi.Enums.Currencies;

import java.math.BigDecimal;

public record UserCheckResponse(
        Long id,
        String name,
        Currencies currencies,
        BigDecimal balance,
        Long userId
) { }