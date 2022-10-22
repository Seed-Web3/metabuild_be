package com.seed.careerhub.jpa;

import com.seed.careerhub.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
