package com.core.job.repotiory;

import com.core.job.model.Job;
import com.core.job.repotiory.querydsl.CustomJobRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long>, CustomJobRepository {
}