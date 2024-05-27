package com.service.deal.mapper;

import com.core.deal.model.ProtectedDeal;
import com.service.deal.dto.CreateProtectedDealDto;
import com.service.deal.dto.ProtectedDealResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-26T22:39:23+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class ProtectedDealMapperImpl implements ProtectedDealMapper {

    @Override
    public ProtectedDealResponse toDto(ProtectedDeal protectedDeal) {
        if ( protectedDeal == null ) {
            return null;
        }

        ProtectedDealResponse.ProtectedDealResponseBuilder protectedDealResponse = ProtectedDealResponse.builder();

        protectedDealResponse.id( protectedDeal.getId() );
        protectedDealResponse.getterId( protectedDeal.getGetterId() );
        protectedDealResponse.providerId( protectedDeal.getProviderId() );
        protectedDealResponse.bond( protectedDeal.getBond() );

        protectedDealResponse.charge( protectedDeal.calculateCharge() );
        protectedDealResponse.totalPrice( protectedDeal.calculateFinalPayPrice() );

        return protectedDealResponse.build();
    }

    @Override
    public ProtectedDeal toEntity(CreateProtectedDealDto dto) {
        if ( dto == null ) {
            return null;
        }

        ProtectedDeal.ProtectedDealBuilder protectedDeal = ProtectedDeal.builder();

        protectedDeal.getterId( dto.getGetterId() );
        protectedDeal.providerId( dto.getProviderId() );
        protectedDeal.bond( dto.getBond() );

        protectedDeal.dealState( com.core.deal.model.DealState.DURING );

        return protectedDeal.build();
    }
}
