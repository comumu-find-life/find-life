package com.common.deal.mapper;

import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.common.deal.response.MyProtectedDealResponse;
import com.common.deal.response.ProtectedDealGeneratorResponse;
import com.common.deal.response.ProtectedDealResponse;
import com.common.deal.response.ProtectedDealOverViewResponse;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.model.ProtectedDealDateTime;
import com.core.api_core.home.model.Home;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface ProtectedDealMapper {

    ProtectedDealMapper INSTANCE = Mappers.getMapper(ProtectedDealMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dealState", expression = "java(com.core.api_core.deal.model.DealState.REQUEST_DEAL)")
    @Mapping(target = "protectedDealDateTime", expression = "java(createProtectedDealDateTime())") // Set current time
    @Mapping(target = "secretKey", source = "secretKey")
    ProtectedDeal toEntity(ProtectedDealGeneratorRequest request, String secretKey);


    default ProtectedDealDateTime createProtectedDealDateTime() {
        ProtectedDealDateTime protectedDealDateTime = new ProtectedDealDateTime();
        protectedDealDateTime.setCreateAt(LocalDateTime.now());
        return protectedDealDateTime;
    }

    @Mappings({
            @Mapping(target = "id", expression = "java(deal.getId())"),
            @Mapping(target = "dealState", source = "deal.dealState"),
            @Mapping(target = "deposit", source = "deal.deposit"),
            @Mapping(target = "fee", constant = "0"),
            @Mapping(target = "totalPrice", source = "deal.deposit"),
            @Mapping(target = "createAt", expression = "java(deal.getProtectedDealDateTime().getCreateAt())"),
            @Mapping(target = "startAt", expression = "java(deal.getProtectedDealDateTime().getStartAt())"),
            @Mapping(target = "cancelAt", expression = "java(deal.getProtectedDealDateTime().getCancelAt())"),
            @Mapping(target = "completeAt", expression = "java(deal.getProtectedDealDateTime().getCompleteAt())"),
            @Mapping(target = "address", expression = "java(home.getHomeAddress().parseAddress())"),
            @Mapping(target = "homeImage", expression = "java(home.getMainImage())"),
            @Mapping(target = "rent", expression = "java(home.getHomeInfo().getRent())"),
            @Mapping(target = "bill", expression = "java(home.getHomeInfo().getBill())"),
            @Mapping(target = "bond", expression = "java(home.getHomeInfo().getBond())")
    })
    ProtectedDealResponse toGetterResponse(ProtectedDeal deal, Home home);


    @Mappings({
            @Mapping(target = "id", source = "deal.id"),
            @Mapping(target = "dealState", source = "deal.dealState"),
            @Mapping(target = "deposit", source = "deal.deposit"),
            @Mapping(target = "fee", constant = "0"),
            @Mapping(target = "totalPrice", source = "deal.deposit"),
            @Mapping(target = "createAt", expression = "java(deal.getProtectedDealDateTime().getCreateAt())"),
            @Mapping(target = "startAt", expression = "java(deal.getProtectedDealDateTime().getStartAt())"),
            @Mapping(target = "cancelAt", expression = "java(deal.getProtectedDealDateTime().getCancelAt())"),
            @Mapping(target = "completeAt", expression = "java(deal.getProtectedDealDateTime().getCompleteAt())"),
            @Mapping(target = "address", expression = "java(home.getHomeAddress().parseAddress())"),
            @Mapping(target = "homeImage", expression = "java(home.getMainImage())"),
            @Mapping(target = "rent", expression = "java(home.getHomeInfo().getRent())"),
            @Mapping(target = "bill", expression = "java(home.getHomeInfo().getBill())"),
    })

    MyProtectedDealResponse toMyProtectedDealResponse(ProtectedDeal deal, Home home);

    ProtectedDealOverViewResponse toAdminOverViewResponse(ProtectedDeal entity);

    @Mapping(target = "secretKey", source = "secretKey")
    @Mapping(target = "dealId", source = "dealId")
    ProtectedDealGeneratorResponse toGeneratorResponse(Long dealId, String secretKey);

//    default ProviderAccount toProviderAccount(ProtectedDealGeneratorRequest request) {
//        return ProviderAccount.builder()
//                .account(request.getAccount())
//                .accountHolder(request.getAccountHolder())
//                .bankName(request.getBankName())
//                .build();
//    }
}