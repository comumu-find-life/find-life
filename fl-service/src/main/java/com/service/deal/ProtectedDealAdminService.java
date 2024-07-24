package com.service.deal;

import com.common.deal.mapper.ProtectedDealMapper;
import com.core.api_core.deal.repository.ProtectedDealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProtectedDealAdminService {

    private final ProtectedDealMapper mapper;
    private final ProtectedDealRepository dealRepository;


}
