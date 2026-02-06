package com.example.corebankingapi.Controllers;

import com.example.corebankingapi.DTO.TransactionsResponse;
import com.example.corebankingapi.DTO.UserCheckResponse;
import com.example.corebankingapi.DTO.UserResponse;
import com.example.corebankingapi.Entities.Transaction;

import com.example.corebankingapi.Entities.User;
import com.example.corebankingapi.Entities.UserCheck;
import com.example.corebankingapi.Errors.EntityNotFoundException;
import com.example.corebankingapi.Records.TransferRequest;
import com.example.corebankingapi.Services.TransactionService;
import com.example.corebankingapi.Services.UserCheckService;
import com.example.corebankingapi.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")

public class BankController {

    private final UserService userService;
    private final UserCheckService userCheckService;
    private final TransactionService transactionService;

    public BankController(UserService userService, UserCheckService userCheckService, TransactionService transactionService) {
        this.userService = userService;
        this.userCheckService = userCheckService;
        this.transactionService = transactionService;
    }


    @PostMapping("/users") //Создать пользователя
    public void createUser(@Valid @RequestBody User user) {
        userService.createUser(user);
    }

    @DeleteMapping("/users/{id}") //Удалить пользователя
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/users/{id}/checks") //Создать счёт для пользователя по id
    public void createCheck(@Valid @RequestBody UserCheck userCheck, @PathVariable Long id) {
        User user = userService.findById(id);
        userCheckService.createUserCheck(user, userCheck);
    }

    @GetMapping("/setBalance/{id}/{balance}")
    public void setBalance(@PathVariable Long id, @PathVariable("balance") BigDecimal bigDecimal) {
        userCheckService.setBalance(id, bigDecimal);
    }

    @DeleteMapping("/users/{id}/checks/{checkId}") //Удалить счёт пользователя (по id пользователя и счёта)
    public ResponseEntity<?> deleteCheck(@PathVariable Long id, @PathVariable Long checkId) {
        if (userCheckService.deleteUserCheck(checkId)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Счёт пользователя " + id + " удалён");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EntityNotFoundException("Такого счёта не существует"));
        }
    }

    @GetMapping("/users/{id}/checks/{checkId}/history") //Получить историю по дате/без даты
    public ResponseEntity<List<Transaction>> getHistory(@PathVariable Long id, @PathVariable Long checkId, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime) {
        UserCheck userCheck = userCheckService.findById(checkId);
        if (!userCheck.getUser().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Transaction> history = transactionService.getHistory(userCheck, localDateTime);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/transactions/send") // Создать транзакцию (отправить деньги)
    public void sendMoney(@RequestBody TransferRequest transferRequest) {
        UserCheck fromCheck = userCheckService.findById(transferRequest.senderCheckId());
        UserCheck toCheck = userCheckService.findById(transferRequest.recipientCheckId());
        //BigDecimal amount = transferRequest.amount();

        transactionService.createTransaction(transferRequest, fromCheck, toCheck);
    }

    @GetMapping("/info/users/{id}") // Получить ограниченную информацию о пользователе
    public UserResponse findUserInfo(@PathVariable Long id) {
        return userService.getInfoUser(id);
    }

    @GetMapping("/info/checks/{id}") // Получить ограниченную информацию о счёте
    public UserCheckResponse findCheckInfo(@PathVariable Long id) {
        return userCheckService.getInfoCheck(id);
    }

    @GetMapping("/info/transactions/{id}") // Получить ограниченную информацию о транзакции
    public TransactionsResponse findTransactionInfo(@PathVariable Long id) {
        return transactionService.getInfoTransaction(id);
    }

}
