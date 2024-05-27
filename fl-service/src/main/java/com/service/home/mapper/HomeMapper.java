package com.service.home.mapper;

import com.core.home.model.Home;
import com.core.home.model.HomeAddress;
import com.core.home.model.HomeImage;
import com.core.user.model.User;
import com.service.home.dto.HomeOverviewResponse;
import com.service.home.dto.request.HomeAddressGeneratorRequest;
import com.service.home.dto.request.HomeGeneratorRequest;
import com.service.home.dto.response.HomeInformationResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HomeMapper {

    HomeMapper INSTANCE = Mappers.getMapper(HomeMapper.class);

    /**
     * Home 엔티티 변환
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "status", expression = "java(com.core.home.model.HomeStatus.FOR_SALE)")
    @Mapping(target = "viewCount", ignore = true)
    Home toEntity(HomeGeneratorRequest homeDto);

    /**
     * HomeAddress 엔티티 변환
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    HomeAddress toAddressEntity(HomeAddressGeneratorRequest homeAddressDto);


    /**
     * Home 게시글을 보여줄 DTO 변환
     */
    /**
     * Home 게시글을 보여줄 DTO 변환
     */
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "providerId", source = "user.id")
    @Mapping(target = "providerProfileUrl", source = "user.profileUrl")
    @Mapping(target = "providerName", source = "user.nickName")
    @Mapping(target = "address", source = "home.homeAddress", qualifiedByName = "mapSimpleAddress")
    @Mapping(target = "images", source = "home.images", qualifiedByName = "mapImageUrls")
    HomeInformationResponse toHomeInformation(Home home, User user);

    /**
     * Home 을 리스트로 보여줄 DTO 변환
     */
    @Mapping(target = "address", source = "homeAddress", qualifiedByName = "mapSimpleAddress")
    @Mapping(target = "latitude", source = "homeAddress.latitude")
    @Mapping(target = "longitude", source = "homeAddress.longitude")
    @Mapping(target = "mainImage", source = "images", qualifiedByName = "mapMainImage")
    HomeOverviewResponse toSimpleHomeDto(Home home);

    HomeAddressGeneratorRequest toAddressDto(HomeAddress address);

    /**
     * 커스텀 메서드
     */
    default Home toHomeEntity(HomeGeneratorRequest homeDto) {
        Home home = toEntity(homeDto);
        List<HomeImage> homeImages = mapHomeImages(homeDto.getImages(), home);
        home.setImages(homeImages);
        return home;
    }


    /**
     * 이미지 매핑 메서드
     */
    default List<HomeImage> mapHomeImages(List<String> images, Home home) {
        return images.stream()
                .map(url -> HomeImage.builder()
                        .home(home) // 연관관계 설정
                        .imageUrl(url)
                        .build())
                .collect(Collectors.toList());
    }


    @Named("mapImageUrls")
    default List<String> mapImageUrls(List<HomeImage> images) {
        return images.stream()
                .map(HomeImage::getImageUrl)
                .collect(Collectors.toList());
    }


    @Named("mapMainImage")
    default String mapMainImage(List<HomeImage> images) {
        return images.get(0).getImageUrl();
    }

    @Named("mapSimpleAddress")
    default String mapSimpleAddress(HomeAddress homeAddress) {
        StringBuilder sb = new StringBuilder();

        sb.append(homeAddress.getStreetCode() + " ");
        sb.append(homeAddress.getStreetName() + " ");
        sb.append(", ");
        sb.append(homeAddress.getCity() + " ");
        sb.append(homeAddress.getState() + " ");
        sb.append(homeAddress.getPostCode());

        return sb.toString();
    }

}
