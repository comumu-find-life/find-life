package com.core.job.repotiory.querydsl;


import com.core.job.model.Job;

import java.util.List;


public interface CustomJobRepository {
    List<Job> findByFilter();
}