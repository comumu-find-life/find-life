package com.service.home.mapper;

import com.core.home.model.Home;
import com.core.home.model.HomeAddress;
import com.core.home.model.HomeImage;
import com.service.home.dto.HomeAddressDto;
import com.service.home.dto.HomeDto;
import com.service.home.dto.SimpleHomeDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HomeMapper {

    HomeMapper INSTANCE = Mappers.getMapper(HomeMapper.class);

    /**
     * 매핑 메서드
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    Home toEntity(HomeDto homeDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    HomeAddress toAddressEntity(HomeAddressDto homeAddressDto);

    @Mapping(target = "homeAddress", source = "homeAddress")
    @Mapping(target = "images", source = "images", qualifiedByName = "mapImageUrls")
    HomeDto toDto(Home home);

    @Mapping(target = "address", source = "homeAddress", qualifiedByName = "mapSimpleAddress")
    @Mapping(target = "mainImage", source = "images", qualifiedByName = "mapMainImage")
    SimpleHomeDto toSimpleHomeDto(Home home);

    HomeAddressDto toAddressDto(HomeAddress address);

    /**
     * 커스텀 메서드
     */
    default Home toHomeEntity(HomeDto homeDto){
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
    default String mapSimpleAddress(HomeAddress homeAddress){
        StringBuilder sb = new StringBuilder();

        sb.append(homeAddress.getCity());
        sb.append(" ");
        sb.append(homeAddress.getPostCode());

        return sb.toString();
    }

}
