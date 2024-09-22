package com.common.deal.mapper;

import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.common.deal.response.MyProtectedDealResponse;
import com.common.deal.response.ProtectedDealByGetterResponse;
import com.common.deal.response.ProtectedDealByProviderResponse;
import com.common.deal.response.ProtectedDealOverViewResponse;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.model.ProtectedDealDateTime;
import com.core.api_core.deal.model.ProviderAccount;
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
    @Mapping(target = "providerAccount", expression = "java(toProviderAccount(request))")
    @Mapping(target = "randomDepositorName", expression = "java(uuid)")
    @Mapping(target = "dealState", expression = "java(com.core.api_core.deal.model.DealState.BEFORE_DEPOSIT)")
    @Mapping(target = "protectedDealDateTime", expression = "java(createProtectedDealDateTime())") // Set current time
    ProtectedDeal toEntity(ProtectedDealGeneratorRequest request, String uuid);

    @Mappings({
            @Mapping(target = "account", expression = "java(deal.getProviderAccount().getAccount())"),
            @Mapping(target = "accountHolder", expression = "java(deal.getProviderAccount().getAccountHolder())"),
            @Mapping(target = "bankName", expression = "java(deal.getProviderAccount().getBankName())"),
            @Mapping(target = "id", expression = "java(deal.getId())"),
            @Mapping(target = "dealState", source = "deal.dealState"),
            @Mapping(target = "deposit", source = "deal.deposit"),
            @Mapping(target = "fee", constant = "0"),
            @Mapping(target = "totalPrice", source = "deal.deposit"),
            @Mapping(target = "dealStartDateTime", expression = "java(deal.getProtectedDealDateTime().getDealStartDateTime())"),
            @Mapping(target = "depositRequestDateTime", expression = "java(deal.getProtectedDealDateTime().getDepositRequestDateTime())"),
            @Mapping(target = "depositCompletionDateTime", expression = "java(deal.getProtectedDealDateTime().getDepositCompletionDateTime())"),
            @Mapping(target = "dealCompletionRequestDateTime", expression = "java(deal.getProtectedDealDateTime().getDealCompletionRequestDateTime())"),
            @Mapping(target = "dealCompletionDateTime", expression = "java(deal.getProtectedDealDateTime().getDealCompletionDateTime())"),
            @Mapping(target = "dealCancellationDateTime", expression = "java(deal.getProtectedDealDateTime().getDealCancellationDateTime())"),
            @Mapping(target = "address", expression = "java(home.getHomeAddress().parseAddress())"),
            @Mapping(target = "homeImage", expression = "java(home.getMainImage())"),
            @Mapping(target = "rent", expression = "java(home.getHomeInfo().getRent())"),
            @Mapping(target = "bill", expression = "java(home.getHomeInfo().getBill())")
    })
    ProtectedDealByProviderResponse toProviderResponse(ProtectedDeal deal, Home home);

    default ProtectedDealDateTime createProtectedDealDateTime() {
        ProtectedDealDateTime protectedDealDateTime = new ProtectedDealDateTime();
        protectedDealDateTime.setDealStartDateTime(LocalDateTime.now());
        return protectedDealDateTime;
    }

    @Mappings({
            @Mapping(target = "randomDepositorName", source = "deal.randomDepositorName"),
            @Mapping(target = "id", expression = "java(deal.getId())"),
            @Mapping(target = "dealState", source = "deal.dealState"),
            @Mapping(target = "deposit", source = "deal.deposit"),
            @Mapping(target = "fee", constant = "0"),
            @Mapping(target = "totalPrice", source = "deal.deposit"),
            @Mapping(target = "dealStartDateTime", expression = "java(deal.getProtectedDealDateTime().getDealStartDateTime())"),
            @Mapping(target = "depositRequestDateTime", expression = "java(deal.getProtectedDealDateTime().getDepositRequestDateTime())"),
            @Mapping(target = "depositCompletionDateTime", expression = "java(deal.getProtectedDealDateTime().getDepositCompletionDateTime())"),
            @Mapping(target = "dealCompletionRequestDateTime", expression = "java(deal.getProtectedDealDateTime().getDealCompletionRequestDateTime())"),
            @Mapping(target = "dealCompletionDateTime", expression = "java(deal.getProtectedDealDateTime().getDealCompletionDateTime())"),
            @Mapping(target = "dealCancellationDateTime", expression = "java(deal.getProtectedDealDateTime().getDealCancellationDateTime())"),
            @Mapping(target = "address", expression = "java(home.getHomeAddress().parseAddress())"),
            @Mapping(target = "homeImage", expression = "java(home.getMainImage())"),
            @Mapping(target = "rent", expression = "java(home.getHomeInfo().getRent())"),
            @Mapping(target = "bill", expression = "java(home.getHomeInfo().getBill())"),
            @Mapping(target = "bond", expression = "java(home.getHomeInfo().getBond())")
    })
    ProtectedDealByGetterResponse toGetterResponse(ProtectedDeal deal, Home home);


    @Mappings({
            @Mapping(target = "id", source = "deal.id"),
            @Mapping(target = "dealState", source = "deal.dealState"),
            @Mapping(target = "deposit", source = "deal.deposit"),
            @Mapping(target = "fee", constant = "0"),
            @Mapping(target = "totalPrice", source = "deal.deposit"),
            @Mapping(target = "dealStartDateTime", expression = "java(deal.getProtectedDealDateTime().getDealStartDateTime())"),
            @Mapping(target = "depositRequestDateTime", expression = "java(deal.getProtectedDealDateTime().getDepositRequestDateTime())"),
            @Mapping(target = "depositCompletionDateTime", expression = "java(deal.getProtectedDealDateTime().getDepositCompletionDateTime())"),
            @Mapping(target = "dealCompletionRequestDateTime", expression = "java(deal.getProtectedDealDateTime().getDealCompletionRequestDateTime())"),
            @Mapping(target = "dealCompletionDateTime", expression = "java(deal.getProtectedDealDateTime().getDealCompletionDateTime())"),
            @Mapping(target = "dealCancellationDateTime", expression = "java(deal.getProtectedDealDateTime().getDealCancellationDateTime())"),
            @Mapping(target = "address", expression = "java(home.getHomeAddress().parseAddress())"),
            @Mapping(target = "homeImage", expression = "java(home.getMainImage())"),
            @Mapping(target = "rent", expression = "java(home.getHomeInfo().getRent())"),
            @Mapping(target = "bill", expression = "java(home.getHomeInfo().getBill())"),
    })

    MyProtectedDealResponse toMyProtectedDealResponse(ProtectedDeal deal, Home home);

    ProtectedDealOverViewResponse toAdminOverViewResponse(ProtectedDeal entity);

    default ProviderAccount toProviderAccount(ProtectedDealGeneratorRequest request) {
        return ProviderAccount.builder()
                .account(request.getAccount())
                .accountHolder(request.getAccountHolder())
                .bankName(request.getBankName())
                .build();
    }
}