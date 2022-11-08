package com.seed.careerhub.jpa;

import com.seed.careerhub.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByNameContainingIgnoreCase(String tag);
    Optional<Skill> findOneByName(String tag);
}
