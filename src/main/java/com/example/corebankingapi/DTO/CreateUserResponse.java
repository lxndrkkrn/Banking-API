package com.example.corebankingapi.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserResponse(
        @NotBlank(message = "Имя не может быть пустым")
        @Size(min = 3, max = 16)
        String name,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 6)
        String password
) {
}
