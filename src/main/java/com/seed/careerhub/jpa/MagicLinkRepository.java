package com.seed.careerhub.jpa;

import com.seed.careerhub.domain.MagicLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagicLinkRepository extends JpaRepository<MagicLink, Long> {
}
