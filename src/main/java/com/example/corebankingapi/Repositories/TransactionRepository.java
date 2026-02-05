package com.example.corebankingapi.Repositories;

import com.example.corebankingapi.Entities.Transaction;
import com.example.corebankingapi.Entities.UserCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCheckOrderByDateTimeDesc(UserCheck check);

    List<Transaction> findByCheckAndDateTimeAfterOrderByDateTimeDesc(UserCheck check, LocalDateTime dateTime);

}
