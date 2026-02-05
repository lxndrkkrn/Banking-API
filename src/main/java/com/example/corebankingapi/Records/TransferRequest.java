package com.example.corebankingapi.Records;

import com.example.corebankingapi.Enums.Currencies;
import com.example.corebankingapi.Enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferRequest(
        Long senderCheckId,
        Long recipientCheckId,
        TransactionType transactionType,
        Currencies currencies,
        BigDecimal amount,
        String description,
        LocalDateTime localDateTime
) {}
