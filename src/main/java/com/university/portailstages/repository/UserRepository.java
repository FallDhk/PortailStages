package com.university.portailstages.repository;

import com.university.portailstages.entity.Role;
import com.university.portailstages.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
//    Optional<User> findByRole(Role role);
    List<User> findByRole(Role role);

    List<User> findAll();
}
