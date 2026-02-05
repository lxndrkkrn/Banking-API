package com.example.corebankingapi.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 16,  message = "Имя должно быть от 3 до 16 символов!")
    private String name;

    @NotBlank
    @Size(min = 6, message = "Пароль должен содержать не менее 6 символов")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserCheck> checks;

}
