package com.example.corebankingapi.Entities;

import com.example.corebankingapi.Enums.Currencies;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "checks")
@Setter
@Getter

public class UserCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank // Имя
    @Column(nullable = false)
    @Size(min = 3, max = 16, message = "Название счёта должно содержать от 3 до 16 символов")
    private String name;

    @NotNull // Валюта
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    //@Pattern(regexp = "RUB|USD|EUR|CNY|KZT|CUP|CAD|PLN|VND|BRL")
    private Currencies currencies;

    @Min(0) // Балагнс
    private BigDecimal balance = new BigDecimal(BigInteger.ZERO);

    @ManyToOne // Владелец счёта
    @JoinColumn(name = "user_id")
    private User user;

    // Транзакции, где этот счет — ОТПРАВИТЕЛЬ
    @OneToMany(mappedBy = "check", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> outgoingTransactions;

    // Транзакции, где этот счет — ПОЛУЧАТЕЛЬ
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> incomingTransactions; // Исправлен тип на List<Transaction>

}
