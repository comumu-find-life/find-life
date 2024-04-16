package com.service.home.mapper;

import com.core.home.model.Home;
import com.core.home.model.HomeAddress;
import com.core.home.model.HomeImage;
import com.service.home.dto.HomeAddressDto;
import com.service.home.dto.HomeDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HomeMapper {

    HomeMapper INSTANCE = Mappers.getMapper(HomeMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "homeAddress", source = "homeAddressDto")
    @Mapping(target = "images", source = "images", qualifiedByName = "mapHomeImages")
    @Mapping(target = "viewCount", ignore = true)
    Home toEntity(HomeDto homeDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    HomeAddress toAddressEntity(HomeAddressDto homeAddressDto);


    @Mapping(target = "homeAddressDto", source = "homeAddress")
    @Mapping(target = "images", source = "images", qualifiedByName = "mapImageUrls")
    HomeDto toDto(Home home);

    HomeAddressDto toAddressDto(HomeAddress address);


    @AfterMapping
    default void mapAddress(HomeDto homeDto, @MappingTarget Home home) {
        HomeAddress address = toAddressEntity(homeDto.getHomeAddressDto());
        home.setHomeAddress(address);
    }


    @Named("mapHomeImages")
    default List<HomeImage> mapHomeImages(List<String> images) {
        return images.stream()
                .map(url -> HomeImage.builder()

                        .imageUrl(url).build())
                .collect(Collectors.toList());
    }

    @Named("mapImageUrls")
    default List<String> mapImageUrls(List<HomeImage> images) {
        return images.stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
    }
}
