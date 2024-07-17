package com.core.api_core.deal.repository;

import com.core.api_core.deal.model.ProtectedDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProtectedDealRepository extends JpaRepository<ProtectedDeal, Long>, CustomProtectedDealRepository {

}
