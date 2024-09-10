package com.core.api_core.deal.repository;

import com.core.api_core.deal.model.ProtectedDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProtectedDealRepository extends JpaRepository<ProtectedDeal, Long>, CustomProtectedDealRepository {

}
