package com.example.corebankingapi.DTO;


import java.util.List;

public record UserResponse(
        Long id,
        String name,
        List<UserCheckResponse> checks
) { }
