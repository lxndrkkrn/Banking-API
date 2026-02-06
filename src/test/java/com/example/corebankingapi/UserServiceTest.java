package com.example.corebankingapi;

import com.example.corebankingapi.DTO.UserCheckResponse;
import com.example.corebankingapi.DTO.UserResponse;
import com.example.corebankingapi.Entities.User;
import com.example.corebankingapi.Entities.UserCheck;
import com.example.corebankingapi.Enums.Currencies;
import com.example.corebankingapi.Repositories.UserRepository;
import com.example.corebankingapi.Services.UserCheckService;
import com.example.corebankingapi.Services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

//    @Test
//    void shouldCreateUser() {
//
//        Long userId = 1L;
//        String userName = "Test User";
//        List<UserCheckResponse> userChecks = {new UserCheckResponse(1L, "yuhoj", Currencies.USD, )};
//
//        User user = new User(userId, userName, null);
//
//        userService.createUser();
//
//    }

}
