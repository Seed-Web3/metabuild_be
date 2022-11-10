package com.seed.careerhub.jpa;

import com.seed.careerhub.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findBySkillsContainingIgnoreCase(String skill);
    List<Job> findByLocationContainingIgnoreCase(String location);
}
