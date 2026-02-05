package com.example.corebankingapi.Services;

import com.example.corebankingapi.Entities.User;
import com.example.corebankingapi.Entities.UserCheck;
import com.example.corebankingapi.Repositories.TransactionRepository;
import com.example.corebankingapi.Repositories.UserCheckRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserCheckService {

    private final UserCheckRepository userCheckRepository;
    //private final TransactionRepository transactionRepository;

    public UserCheckService(UserCheckRepository userCheckRepository, TransactionRepository transactionRepository) {
        this.userCheckRepository = userCheckRepository;
        //this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void createUserCheck(User user, UserCheck userCheck) {
        user.getChecks().add(userCheck);
        userCheck.setUser(user);
        userCheckRepository.save(userCheck);
    }

    @Transactional
    public boolean deleteUserCheck(Long id) {
        if (userCheckRepository.existsById(id)) {
            userCheckRepository.deleteById(id);
            return true;

        } else {
            return false;
        }
    }

    public UserCheck findById(Long id) {
        return userCheckRepository.findById(id).orElseThrow(() -> new RuntimeException("Счёт не найден"));
    }

    public List<UserCheck> findByIdList(Long id) {
        return userCheckRepository.findByUserId(id);
    }

}