package com.seed.careerhub.jpa;

import com.seed.careerhub.domain.UserNonce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNonceRepository extends JpaRepository<UserNonce, String> {
}
