package com.seed.careerhub.jpa;

import com.seed.careerhub.domain.MagicLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MagicLinkRepository extends JpaRepository<MagicLink, Long> {

    Optional<MagicLink> findOneByCode(String code);
}
