package com.service.deal;

import com.core.deal.model.ProtectedDeal;
import com.core.deal.repository.ProtectedDealRepository;
import com.service.deal.dto.CreateProtectedDealDto;
import com.service.deal.dto.ProtectedDealResponse;
import com.service.deal.mapper.ProtectedDealMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProtectedDealService {

    private final ProtectedDealRepository repository;
    private final ProtectedDealMapper mapper;

    //안전 결제 생성 메서드
    public Long createDeal(CreateProtectedDealDto createProtectedDealDto) {
        ProtectedDeal deal = mapper.toEntity(createProtectedDealDto);
        return repository.save(deal).getId();
    }

    public ProtectedDealResponse findById(Long id) {
        Optional<ProtectedDeal> entity = repository.findById(id);
        return mapper.toDto(entity.get());
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }


}
