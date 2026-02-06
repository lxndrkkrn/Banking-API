package com.example.corebankingapi.Entities;

import com.example.corebankingapi.Enums.Currencies;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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
    @Size(min = 3, max = 16, message = "Название счёта должно содержать от 3 до 16 символов")
    private String name;

    @NotNull // Валюта
    @Enumerated(EnumType.STRING)
    //@Pattern(regexp = "RUB|USD|EUR|CNY|KZT|CUP|CAD|PLN|VND|BRL")
    private Currencies currencies;

    @Min(0) // Балагнс
    private BigDecimal balance;

    @ManyToOne // Владелец счёта
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "check", cascade = CascadeType.ALL) // Список транзакций, как для отправителя
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL) // Список транзакций, как для получателя
    private List<UserCheck> checks;

}
