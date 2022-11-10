package com.seed.careerhub.jpa;

import com.seed.careerhub.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
