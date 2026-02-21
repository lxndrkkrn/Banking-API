package com.example.corebankingapi.Repositories;

import com.example.corebankingapi.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

}
