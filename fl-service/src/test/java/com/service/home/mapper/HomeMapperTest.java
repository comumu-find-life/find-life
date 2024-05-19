package com.service.home.mapper;

import com.core.home.model.Home;
import com.core.home.model.HomeAddress;
import com.core.home.model.HomeImage;
import com.service.home.dto.request.HomeAddressGeneratorRequest;
import com.service.home.dto.request.HomeGeneratorRequest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HomeMapperTest {

    private HomeMapper homeMapper = HomeMapper.INSTANCE;

    @Test
    void Home_to_HomeDto_변경_테스트() {
        //given
        Home home = generateHomeEntity();
        //when
        HomeGeneratorRequest homeDto = homeMapper.toDto(home);

        //then
        assertThat(homeDto.getImages().size()).isEqualTo(1);
        assertThat(homeDto.getHomeAddress().getCity()).isEqualTo("city name");
    }


    @Test
    void HomeDto_to_Home_변경_테스트() {
        //given
        HomeGeneratorRequest homeDto = generateHomeDto();

        //when
        Home entity = homeMapper.toHomeEntity(homeDto);

        //then
        assertThat(entity.getImages().get(0).getHome()).isEqualTo(entity);
        assertThat(entity.getHomeAddress().getCity()).isEqualTo("test city");
        assertThat(entity.getUserId()).isEqualTo(1L);
    }

    private HomeGeneratorRequest generateHomeDto() {
        HomeAddressGeneratorRequest addressDto = HomeAddressGeneratorRequest.builder()
                .city("test city")
                .postCode(1234)
                .build();

        List<String> images = new ArrayList<>();

        images.add("url1");
        images.add("url2");
        images.add("url3");

        HomeGeneratorRequest homeDto = HomeGeneratorRequest.builder()
                .homeAddress(addressDto)
                .userId(1L)
                .images(images)
                .bill(20000)
                .bond(10000)
                .build();

        return homeDto;
    }

    private Home generateHomeEntity() {
        HomeAddress homeAddress = HomeAddress.builder()
                .id(1L)
                .state("WBC")
                .city("city name")
                .postCode(1234)
                .build();

        Home home = Home.builder()
                .id(1L)
                .userId(1L)
                .homeAddress(homeAddress)
                .build();

        List<HomeImage> images = new ArrayList<>();
        images.add(HomeImage.builder()
                .id(1L)
                .imageUrl("url1")
                .home(home)
                .build());


        home.setImages(images);

        return home;
    }


}
