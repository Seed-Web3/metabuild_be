package com.seed.careerhub.jpa;

import com.seed.careerhub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEthAddress(String ethAddress);
    User findByNearAddress(String ethAddress);
    Optional<User> findOneByEmail(String ethAddress);
}
