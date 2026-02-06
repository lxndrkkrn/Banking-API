package com.example.corebankingapi.Services;

import com.example.corebankingapi.Entities.User;
import com.example.corebankingapi.Errors.EntityNotFoundException;
import com.example.corebankingapi.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void createUser(User user) {
        log.info("Попытка создания пользователя: {}", user);

        try {
            userRepository.save(user);

            log.info("Счёт успешно создан: {}", user);

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

}