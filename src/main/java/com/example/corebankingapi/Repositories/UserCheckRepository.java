package com.example.corebankingapi.Repositories;

import com.example.corebankingapi.Entities.UserCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCheckRepository extends JpaRepository<UserCheck, Long> {

    List<UserCheck> findByUserId(Long id);

}
