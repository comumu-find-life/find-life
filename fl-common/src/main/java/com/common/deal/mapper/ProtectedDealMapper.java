package com.common.deal.mapper;

import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.common.deal.response.ProtectedDealOverViewResponse;
import com.common.deal.response.ProtectedDealResponse;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.model.ProviderAccount;
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

    ProtectedDealOverViewResponse toAdminOverViewResponse(ProtectedDeal entity);

    default ProviderAccount toProviderAccount(ProtectedDealGeneratorRequest request) {
        return ProviderAccount.builder()
                .account(request.getAccount())
                .accountHolder(request.getAccountHolder())
                .bankName(request.getBankName())
                .build();
    }
}