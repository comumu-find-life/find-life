package com.core.domain.home.repository;

import com.core.domain.home.model.Home;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeRepository extends JpaRepository<Home, Long>, CustomHomeRepository {
}
