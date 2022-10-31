package com.seed.careerhub.jpa;

import com.seed.careerhub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByNearAddress(String nearAddress);
    Optional<User> findOneByEmail(String emailAddress);
}
