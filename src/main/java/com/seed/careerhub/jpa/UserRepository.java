package com.seed.careerhub.jpa;

import com.seed.careerhub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEthAddress(String ethAddress);
    User findByNearAddress(String ethAddress);
}
