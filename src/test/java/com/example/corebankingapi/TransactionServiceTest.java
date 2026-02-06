package com.example.corebankingapi;

import com.example.corebankingapi.Entities.UserCheck;
import com.example.corebankingapi.Enums.Currencies;
import com.example.corebankingapi.Enums.TransactionType;
import com.example.corebankingapi.Errors.InsufficientFundsException;
import com.example.corebankingapi.Records.TransferRequest;
import com.example.corebankingapi.Repositories.TransactionRepository;
import com.example.corebankingapi.Repositories.UserCheckRepository;
import com.example.corebankingapi.Services.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private UserCheckRepository userCheckRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    @DisplayName("Транзакция не будет создана")
    void shouldThrowExceptionWhenInsufficientFunds() {

        Long fromId = 1L;
        Long toId = 2L;
        BigDecimal amount = new BigDecimal("1000");

        UserCheck fromCheck = new UserCheck();
        fromCheck.setId(fromId);
        fromCheck.setBalance(new BigDecimal("500"));

        UserCheck toCheck = new UserCheck();
        toCheck.setId(toId);
        toCheck.setBalance(BigDecimal.ZERO);

        TransferRequest transferRequest = new TransferRequest(
                fromId,
                toId,
                TransactionType.Payments,
                Currencies.USD,
                amount,
                "Test payments",
                LocalDateTime.now()
        );

        assertThrows(InsufficientFundsException.class, () -> {
            transactionService.createTransaction(transferRequest, fromCheck, toCheck);
        });
    }

    @Test
    @DisplayName("Транзакция должна создасться")
    void shouldTransferMoneySuccessfully() {

        Long fromId = 1L;
        Long toId = 2L;
        BigDecimal amount = new BigDecimal("300");

        UserCheck fromCheck = new UserCheck();
        fromCheck.setId(fromId);
        fromCheck.setBalance(new BigDecimal("1000"));

        UserCheck toCheck = new UserCheck();
        toCheck.setId(toId);
        toCheck.setBalance(new BigDecimal("0"));

        TransferRequest transferRequest = new TransferRequest(
                fromId,
                toId,
                TransactionType.Payments,
                Currencies.USD,
                amount,
                "Gift",
                LocalDateTime.now()
        );

        transactionService.createTransaction(transferRequest, fromCheck, toCheck);
    }
}
