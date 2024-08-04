package com.common.deal.mapper;

import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.common.deal.response.ProtectedDealOverViewResponse;
import com.common.deal.response.ProtectedDealResponse;
import com.common.deal.response.ProtectedDealResponseV2;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.model.ProviderAccount;
import com.core.api_core.home.model.Home;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProtectedDealMapper {

    ProtectedDealMapper INSTANCE = Mappers.getMapper(ProtectedDealMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "providerAccount", expression = "java(toProviderAccount(request))")
    @Mapping(target = "randomDepositorName", expression = "java(uuid)")
    @Mapping(target = "dealState", expression = "java(com.core.api_core.deal.model.DealState.BEFORE_DEPOSIT)")
    ProtectedDeal toEntity(ProtectedDealGeneratorRequest request, String uuid);

    @Mappings({
            @Mapping(target = "account", source = "entity.providerAccount.account"),
            @Mapping(target = "accountHolder", source = "entity.providerAccount.accountHolder"),
            @Mapping(target = "bankName", source = "entity.providerAccount.bankName"),
    })
    ProtectedDealResponse toResponse(ProtectedDeal entity);

    @Mappings({
            @Mapping(target = "id", source = "deal.id"),
            @Mapping(target = "dealState", source = "deal.dealState"),
            @Mapping(target = "deposit", source = "deal.deposit"),
            @Mapping(target = "fee", constant = "0"),
            @Mapping(target = "totalPrice", source = "deal.deposit"),
            @Mapping(target = "startDate", source = "deal.createDate"),
            @Mapping(target = "depositDate", source = "deal.depositDate"),
            @Mapping(target = "finishDate", source = "deal.finishDate"),
            @Mapping(target = "cancelDate", source = "deal.cancelDate"),
            @Mapping(target = "address", expression = "java(home.getHomeAddress().parseAddress())"),
            @Mapping(target = "homeImage", expression = "java(home.getMainImage())"),
            @Mapping(target = "rent", source = "home.rent"),
            @Mapping(target = "bill", source = "home.bill")
    })
    ProtectedDealResponseV2 toResponseV2(ProtectedDeal deal, Home home);

    ProtectedDealOverViewResponse toAdminOverViewResponse(ProtectedDeal entity);

    default ProviderAccount toProviderAccount(ProtectedDealGeneratorRequest request) {
        return ProviderAccount.builder()
                .account(request.getAccount())
                .accountHolder(request.getAccountHolder())
                .bankName(request.getBankName())
                .build();
    }
}