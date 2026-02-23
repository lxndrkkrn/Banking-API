package com.example.corebankingapi;

import com.example.corebankingapi.DTO.CreateUserResponse;
import com.example.corebankingapi.Errors.EmptyValueException;
import com.example.corebankingapi.Repositories.UserRepository;
import com.example.corebankingapi.Services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUser() {
        String userName = "Test User";
        String userPassword = "super_secret_password";

        CreateUserResponse createUserResponse = new CreateUserResponse(
                userName,
                userPassword
        );

        if (createUserResponse.name() == null || createUserResponse.password() == null) {
            throw new EmptyValueException("Не указаны обязательные данные");
        }

        userService.createUser(createUserResponse);
    }

    @Test
    void shouldThrowExceptionNullPassword() {
        String userName = "Test User";
        String userPassword = null;

        CreateUserResponse createUserResponse = new CreateUserResponse(
                userName,
                userPassword
        );

        if (createUserResponse.name() == null || createUserResponse.password() == null) {
            throw new EmptyValueException("Не указаны обязательные данные");
        }

        userService.createUser(createUserResponse);
    }

    @Test
    void shouldThrowExceptionNullName() {
        String userName = null;
        String userPassword = "super_secret_password";

        CreateUserResponse createUserResponse = new CreateUserResponse(
                userName,
                userPassword
        );

        if (createUserResponse.name() == null || createUserResponse.password() == null) {
            throw new EmptyValueException("Не указаны обязательные данные");
        }

        userService.createUser(createUserResponse);
    }

}
