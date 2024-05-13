package com.service.deal;

import com.core.deal.model.DealState;
import com.core.deal.model.ProtectedDeal;
import com.core.deal.repository.ProtectedDealRepository;
import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.service.deal.dto.CreateProtectedDealDto;
import com.service.deal.dto.ProtectedDealResponse;
import com.service.deal.mapper.ProtectedDealMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ProtectedDealService {

    private final UserRepository userRepository;
    private final ProtectedDealRepository protectedDealRepository;
    private final ProtectedDealMapper mapper;

    //안전 거래 생성 메서드
    public Long createDeal(CreateProtectedDealDto createProtectedDealDto) {
        ProtectedDeal deal = mapper.toEntity(createProtectedDealDto);
        // 세입자의 포인트 차감

        User getter = userRepository.findById(createProtectedDealDto.getGetterId()).get();
        getter.decreasePoint(deal.calculateFinalPayPrice());

        return protectedDealRepository.save(deal).getId();
    }

    //안전 거래 조회 메서드
    public ProtectedDealResponse findById(Long id) {
        Optional<ProtectedDeal> entity = protectedDealRepository.findById(id);
        return mapper.toDto(entity.get());
    }

    //안전 거래 완료 메서드 (포인트 집주인에게 전달)
    public void finishProtectedDeal(ProtectedDealResponse protectedDealRequest){
        ProtectedDeal deal = protectedDealRepository.findById(protectedDealRequest.getId()).get();
        deal.setDealState(DealState.FINISH);

        //집주인의 포인트 증가
        User provider = userRepository.findById(protectedDealRequest.getProviderId()).get();
        provider.increasePoint(protectedDealRequest.getTotalPrice());
    }

    //안전 거래 취소 메서드 (포인트 세입자에게 전달)
    public void cancelProtectedDeal(ProtectedDealResponse protectedDealRequest){
        ProtectedDeal deal = protectedDealRepository.findById(protectedDealRequest.getId()).get();
        deal.setDealState(DealState.CANCEL);

        //세입자의 포인트 증가
        User getter = userRepository.findById(protectedDealRequest.getGetterId()).get();
        getter.increasePoint(protectedDealRequest.getTotalPrice());
    }

    public void deleteById(Long id) {
        protectedDealRepository.deleteById(id);
    }


}
