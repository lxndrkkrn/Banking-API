package com.example.corebankingapi.DTO;

import com.example.corebankingapi.Entities.UserCheck;
import com.example.corebankingapi.Enums.Currencies;
import com.example.corebankingapi.Enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionsResponse(
        Long id,
        LocalDateTime dateTime,
        TransactionType transactionType,
        BigDecimal amount,
        Currencies currencies,
        String description
) { }