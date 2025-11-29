package com.banking.portal.repository;

import com.banking.portal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

            Optional<User> findByUsername(String name);
            Optional<User> findByPhone(Long phoneNumber);
            List<User> findByRole(String role);
            Boolean existsByEmail(String email);
            Boolean existsByUsername(String username);
            Boolean existsByPhone(Long phoneNumber);
}
