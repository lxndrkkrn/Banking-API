package com.example.corebankingapi.Entities;

import com.example.corebankingapi.Enums.Currencies;
import com.example.corebankingapi.Enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Setter
@Getter

public class Transaction {

    @Id // Номер транзакции
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull // Получатель (id)
    private Long recipientCheckId;

    @NotNull // Время
    private LocalDateTime dateTime = LocalDateTime.now();

    @NotNull // Тип транзакции
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currencies currencies;

    @NotNull
    private String description;

    @ManyToOne // Отправитель
    @JoinColumn(name = "check_id")
    private UserCheck check;

    @ManyToOne // Получатель
    @JoinColumn(name = "transaction_id")
    private UserCheck transaction;

}
