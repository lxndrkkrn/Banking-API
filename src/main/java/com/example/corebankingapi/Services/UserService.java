package com.example.corebankingapi.Services;

import com.example.corebankingapi.DTO.UserCheckResponse;
import com.example.corebankingapi.DTO.UserResponse;
import com.example.corebankingapi.Entities.User;
import com.example.corebankingapi.Entities.UserCheck;
import com.example.corebankingapi.Errors.EntityNotFoundException;
import com.example.corebankingapi.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private List<UserCheckResponse> mapChecksToResponse(List<UserCheck> userChecks) {
        return userChecks.stream().map(check -> new UserCheckResponse(
                check.getId(),
                check.getName(),
                check.getCurrencies(),
                check.getBalance(),
                check.getUser().getId()
        )).collect(Collectors.toList());
    }

    private User mapUserResponseToUser(UserResponse userDTO) {
        User user = new User();
        user.setName(userDTO.name());
        user.setName(userDTO.name());
        return user;
    }

    @Transactional
    public void createUser(User user) {
        log.info("Попытка создания пользователя: {}", user);

        try {
            userRepository.save(user);

            log.info("Пользователь успешно создан: {}", user);

        } catch (Exception e) {
            log.error("Ошибка при создании пользователя: {}", user);
            throw e;
        }

    }

    @Transactional
    public void deleteUser(Long id) {
        log.info("Попытка удаления пользователя: {}", userRepository.findById(id));

        try {
            userRepository.deleteById(id);

            log.info("Пользователь успешно удалён: {}", userRepository.findById(id));

        } catch (Exception e) {
            log.error("Ошибка удаления пользователя: {}", userRepository.findById(id));
            throw e;
        }

    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }

    public UserResponse getInfoUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь с ID: " + id + " не найден"));
        List<UserCheckResponse> checksDTO = mapChecksToResponse(user.getChecks());

        return new UserResponse(
                user.getId(),
                user.getName(),
                checksDTO
        );
    }

}