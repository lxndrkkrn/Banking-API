package com.example.corebankingapi.Services;

import com.example.corebankingapi.Entities.Transaction;
import com.example.corebankingapi.Entities.UserCheck;
import com.example.corebankingapi.Enums.TransactionType;
import com.example.corebankingapi.Errors.InsufficientFundsException;
import com.example.corebankingapi.Records.TransferRequest;
import com.example.corebankingapi.Repositories.TransactionRepository;
import com.example.corebankingapi.Repositories.UserCheckRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserCheckRepository userCheckRepository;

    public TransactionService(TransactionRepository transactionRepository, UserCheckRepository userCheckRepository) {
        this.transactionRepository = transactionRepository;
        this.userCheckRepository = userCheckRepository;
    }

    public List<Transaction> getHistory(UserCheck userCheck, LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return transactionRepository.findByCheckOrderByDateTimeDesc(userCheck);
        } else {
            return transactionRepository.findByCheckAndDateTimeAfterOrderByDateTimeDesc(userCheck, localDateTime);
        }
    }

    @Transactional
    public void createTransaction(TransferRequest transferRequest, UserCheck fromCheck, UserCheck toCheck) {
        if (fromCheck.getBalance().compareTo(transferRequest.amount()) < 0) {
            throw new InsufficientFundsException("Недостаточно средств на счете");
        }

        fromCheck.setBalance(fromCheck.getBalance().subtract(transferRequest.amount()));
        toCheck.setBalance(toCheck.getBalance().add(transferRequest.amount()));

        userCheckRepository.save(fromCheck);
        userCheckRepository.save(toCheck);

        Transaction tx = new Transaction();
        tx.setCheck(fromCheck);
        tx.setRecipientCheckId(toCheck.getId());
        tx.setAmount(transferRequest.amount());
        tx.setTransactionType(TransactionType.Translation);
        tx.setDateTime(LocalDateTime.now());
        tx.setTransactionType(transferRequest.transactionType());
        tx.setDescription(transferRequest.description());

        transactionRepository.save(tx);

    }

}