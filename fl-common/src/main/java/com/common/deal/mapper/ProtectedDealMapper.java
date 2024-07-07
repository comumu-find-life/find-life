package com.common.deal.mapper;

import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.core.api_core.deal.model.ProtectedDeal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProtectedDealMapper {

    ProtectedDealMapper INSTANCE = Mappers.getMapper(ProtectedDealMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dealState", expression = "java(com.core.api_core.deal.model.DealState.BEFORE_DEPOSIT)")
    ProtectedDeal toEntity(ProtectedDealGeneratorRequest request);
}
