package com.example.corebankingapi.Repositories;

import com.example.corebankingapi.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
