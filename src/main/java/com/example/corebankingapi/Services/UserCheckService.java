package com.example.corebankingapi.Services;

import com.example.corebankingapi.DTO.UserCheckResponse;
import com.example.corebankingapi.Entities.User;
import com.example.corebankingapi.Entities.UserCheck;
import com.example.corebankingapi.Errors.EntityNotFoundException;
import com.example.corebankingapi.Repositories.TransactionRepository;
import com.example.corebankingapi.Repositories.UserCheckRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class UserCheckService {

    private final UserCheckRepository userCheckRepository;
    //private final TransactionRepository transactionRepository;

    public UserCheckService(UserCheckRepository userCheckRepository, TransactionRepository transactionRepository) {
        this.userCheckRepository = userCheckRepository;
        //this.transactionRepository = transactionRepository;
    }

    private UserCheck mapCheckResponseToCheck(UserCheckResponse userCheckResponse) {
        UserCheck userCheck = new UserCheck();
        userCheck.setBalance(BigDecimal.ZERO);
        userCheck.setName(userCheckResponse.name());
        userCheck.setCurrencies(userCheckResponse.currencies());
        return userCheck;
    }

    @Transactional
    public void setBalance(Long id, BigDecimal bigDecimal) {
        UserCheck userCheck = findById(id);
        userCheck.setBalance(bigDecimal);
        System.out.println("Money added");
    }

    @Transactional
    public void createUserCheck(User user, UserCheck userCheck) {
        log.info("Попытка создания счёта: {}", userCheck);

        try {
            user.getChecks().add(userCheck);
            userCheck.setUser(user);
            userCheck.setBalance(BigDecimal.ZERO);

            userCheckRepository.save(userCheck);

            log.info("Счёт успешно создан: {}", userCheck);
        } catch (Exception e) {
            log.error("Счёт не был создан: {}", e.getMessage());
            throw e;
        }


    }

    @Transactional
    public boolean deleteUserCheck(Long id) {
        log.info("Попытка удаления счёта: {}", userCheckRepository.findById(id));

        try {
            if (userCheckRepository.existsById(id)) {
                userCheckRepository.deleteById(id);

                log.info("Счёт успешно удалён: {}", userCheckRepository.findByUserId(id));
                return true;
            } else {
                log.error("Счёт не был удалён, т.к. его не существует: {}", id);
                throw new EntityNotFoundException("Счёт не был удалён, т.к. его не существует: " + id);
            }
        } catch (Exception e) {
            log.error("Счёт не был удалён: {}", e.getMessage());
            throw e;
        }
    }

    public UserCheck findById(Long id) {
        return userCheckRepository.findById(id).orElseThrow(() -> new RuntimeException("Счёт не найден"));
    }

    public List<UserCheck> findByIdList(Long id) {
        return userCheckRepository.findByUserId(id);
    }

    public UserCheckResponse getInfoCheck(Long id) {
        UserCheck userCheck = userCheckRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Счёт с ID: " + id + " не найден"));
        return new UserCheckResponse(
                userCheck.getId(),
                userCheck.getName(),
                userCheck.getCurrencies(),
                userCheck.getBalance(),
                userCheck.getUser().getId()
        );
    }

}