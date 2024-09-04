package com.common.deal;

import com.common.deal.mapper.ProtectedDealMapper;
import com.common.deal.response.MyProtectedDealResponse;
import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.home.model.Home;
import com.core.api_core.home.model.HomeAddress;
import com.core.api_core.home.model.HomeImage;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProtectedDealMapperTest {

    private final ProtectedDealMapper mapper = Mappers.getMapper(ProtectedDealMapper.class);

    @Test
    public void testToResponseV2() {
        // Given
        ProtectedDeal deal = ProtectedDeal.builder()
                .id(1L)
                .dealState(DealState.BEFORE_DEPOSIT)
                .deposit(1000.0)
                .depositDate(LocalDateTime.now())
                .finishDate(LocalDateTime.now().plusDays(1))
                .cancelDate(null)
                .providerAccount(null)
                .build();


        HomeAddress address = HomeAddress.builder()
                .state("NSW")
                .city("Sydney")
                .postCode(2000)
                .detailAddress("401")
                .streetName("George Street")
                .streetCode("100")
                .latitude(-33.8688)
                .longitude(151.2093)
                .build();

        HomeImage homeImage = HomeImage.builder()
                .imageUrl("https://example.com/image1.jpg")
                .build();

        Home home = Home.builder()
                .homeAddress(address)
                .images(Collections.singletonList(homeImage))
                .rent(500)
                .bill(100)
                .build();

        // When
        MyProtectedDealResponse response = mapper.toMyProtectedDealResponse(deal, home);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(DealState.BEFORE_DEPOSIT, response.getDealState());
        assertEquals(1000.0, response.getDeposit());
        assertEquals(0.0, response.getFee());
        assertEquals(1000.0, response.getTotalPrice());
        assertEquals(deal.getDepositDate(), response.getDepositDate());
        assertEquals(deal.getFinishDate(), response.getFinishDate());
        assertEquals(null, response.getCancelDate());
        assertEquals("100 George Street,Sydney NSW 2000", response.getAddress());
        assertEquals(home.getMainImage(), response.getHomeImage());
        assertEquals(500, response.getRent());
        assertEquals(100, response.getBill());
    }
}
