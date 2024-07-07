package com.service.deal.mapper;

import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDeal;
import com.service.deal.dto.CreateProtectedDealDto;
import com.service.deal.dto.ProtectedDealResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProtectedDealMapperTest {

    private ProtectedDealMapper mapper = ProtectedDealMapper.INSTANCE;

    @Test
    void CreateProtectedDealDto_to_ProtectedDeal_변환_테스트(){
        //given
        CreateProtectedDealDto dto = generateCreateProtectedDealDto();

        //when
        ProtectedDeal entity = mapper.toEntity(dto);

        //then
        Assertions.assertThat(entity.getDealState()).isEqualTo(DealState.DURING);
    }

    @Test
    void ProtectedDeal_to_ProtectedDealResponse_변환_테스트(){
        //given
        ProtectedDeal protectedDeal = generateProtectedDeal();
        //when
        ProtectedDealResponse dto = mapper.toDto(protectedDeal);
        //then
        Assertions.assertThat(dto.getCharge()).isEqualTo(300.0);
    }

    private ProtectedDeal generateProtectedDeal(){
        return ProtectedDeal.builder()
                .getterId(1L)
                .providerId(1L)
                .dealState(DealState.DURING)
                .bond(10000)
                .build();
    }

    private CreateProtectedDealDto generateCreateProtectedDealDto(){
        return CreateProtectedDealDto.builder()
                .getterId(1L)
                .providerId(3L)
                .bond(3000)
                .build();
    }
}
