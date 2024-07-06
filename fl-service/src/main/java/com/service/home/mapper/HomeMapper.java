//package com.service.home.mapper;
//
//import com.core.home.model.Home;
//import com.core.home.model.HomeAddress;
//import com.core.home.model.HomeImage;
//import com.core.user.model.User;
//import com.service.home.dto.response.HomeOverviewResponse;
//import com.service.home.dto.request.HomeAddressGeneratorRequest;
//import com.service.home.dto.request.HomeGeneratorRequest;
//import com.service.home.dto.request.HomeUpdateRequest;
//import com.service.home.dto.response.HomeInformationResponse;
//import org.mapstruct.*;
//import org.mapstruct.factory.Mappers;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Mapper(componentModel = "spring")
//public interface HomeMapper {
//
//    HomeMapper INSTANCE = Mappers.getMapper(HomeMapper.class);
//
//    /**
//     * Home 엔티티 변환
//     */
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "images", ignore = true)
//    @Mapping(target = "status", expression = "java(com.core.home.model.HomeStatus.FOR_SALE)")
//    @Mapping(target = "viewCount", ignore = true)
//    //todo userIdx
//    Home toEntity(HomeGeneratorRequest homeDto);
//
//    /**
//     * HomeAddress 엔티티 변환
//     */
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "latitude", ignore = true)
//    @Mapping(target = "longitude", ignore = true)
//    HomeAddress toAddressEntity(HomeAddressGeneratorRequest homeAddressDto);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "images", ignore = true)
//    @Mapping(target = "status", ignore = true)
//
//    @Mapping(target = "viewCount", ignore = true)
//    void updateHomeFromDto(HomeUpdateRequest dto, @MappingTarget Home entity);
//
//
//    /**
//     * Home 게시글을 보여줄 DTO 변환
//     */
//
//    @Mapping(target = "gender", ignore = true)
//    @Mapping(target = "homeId", source = "home.id")
//    @Mapping(target = "latitude", source = "home.homeAddress.latitude")
//    @Mapping(target = "longitude", source = "home.homeAddress.longitude")
//    @Mapping(target = "providerId", source = "user.id")
//    @Mapping(target = "introduce", source = "home.introduce")
//    @Mapping(target = "providerProfileUrl", source = "user.profileUrl")
//    @Mapping(target = "providerName", source = "user.nickname")
//    @Mapping(target = "address", source = "home.homeAddress", qualifiedByName = "mapSimpleAddress")
//    @Mapping(target = "images", source = "home.images", qualifiedByName = "mapImageUrls")
//    HomeInformationResponse toHomeInformation(Home home, User user);
//
//    /**
//     * Home 을 리스트로 보여줄 DTO 변환
//     */
//    @Mapping(target = "id", source = "home.id")
//    @Mapping(target = "address", source = "home.homeAddress", qualifiedByName = "mapSimpleAddress")
//    @Mapping(target = "latitude", source = "home.homeAddress.latitude")
//    @Mapping(target = "longitude", source = "home.homeAddress.longitude")
//    @Mapping(target = "userIdx", source = "user.id")
//    @Mapping(target = "userName", source = "user.nickname")
//    @Mapping(target = "mainImage", source = "home.images", qualifiedByName = "mapMainImage")
//    HomeOverviewResponse toSimpleHomeDto(Home home, User user);
//
//
//
//    @Named("mapImageUrls")
//    default List<String> mapImageUrls(List<HomeImage> images) {
//        return images.stream()
//                .map(HomeImage::getImageUrl)
//                .collect(Collectors.toList());
//    }
//
//
//    @Named("mapMainImage")
//    default String mapMainImage(List<HomeImage> images) {
//        return images.get(0).getImageUrl();
//    }
//
//    @Named("mapSimpleAddress")
//    default String mapSimpleAddress(HomeAddress homeAddress) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(homeAddress.getStreetCode() + " ");
//        sb.append(homeAddress.getStreetName() + " ");
//        sb.append(", ");
//        sb.append(homeAddress.getCity() + " ");
//        sb.append(homeAddress.getState() + " ");
//        sb.append(homeAddress.getPostCode());
//        return sb.toString();
//    }
//
//
//}
