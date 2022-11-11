package com.seed.careerhub.jpa;

import com.seed.careerhub.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    List<Badge> findByNftAccount(String nftAccount);
    List<Badge> findByNftAccountAndNftTokenId(String nftAccount, String nftTokenId);
    List<Badge> findByUserId(Long id);
}
