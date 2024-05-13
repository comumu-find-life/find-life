package com.core.job.repository;

import com.core.job.model.Job;
import com.core.job.repository.querydsl.CustomJobRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long>, CustomJobRepository {
}