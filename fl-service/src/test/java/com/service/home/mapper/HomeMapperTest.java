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
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//
//public class HomeMapperTest {
//
//    private HomeMapper homeMapper = HomeMapper.INSTANCE;
//
//
//    @Test
//    void HomeInformationResponse_변경_테스트() {
//        //given
//        Home home = generateHomeEntity();
//        User user = generateUserEntity();
//        //when
//        HomeInformationResponse homeInformationResponse = homeMapper.toHomeInformation(home, user);
//        //then
//        Assertions.assertThat(homeInformationResponse.getAddress()).isEqualTo("10 BridgeStreet , Sydney NSW 2000");
//        Assertions.assertThat(homeInformationResponse.getProviderName()).isEqualTo("이름");
//        Assertions.assertThat(homeInformationResponse.getImages().size()).isEqualTo(3);
//    }
//
//    @Test
//    void 집_게시글_수정_테스트() {
//        Home home = generateHomeEntity();
//
//        HomeUpdateRequest homeUpdateRequest = generateHomeUpdateRequest();
//
//        homeMapper.updateHomeFromDto(homeUpdateRequest, home);
//
//        Assertions.assertThat(home.getHomeAddress().getCity()).isEqualTo("Change");
//    }
//
//
//    @Test
//    void HomeDto_변경_테스트() {
//        //given
//        HomeGeneratorRequest homeDto = generateHomeDto();
//
//        //when
//        Home entity = homeMapper.toHomeEntity(homeDto);
//
//        //then
//        assertThat(entity.getImages().get(0).getHome()).isEqualTo(entity);
//        assertThat(entity.getHomeAddress().getCity()).isEqualTo("test city");
//        assertThat(entity.getUserId()).isEqualTo(1L);
//    }
//
//    @Test
//    void SimpleHomeDto_변경_테스트() {
//        //given
//        Home home = generateHomeEntity();
//
//        //when
//        HomeOverviewResponse simpleHomeDto = homeMapper.toSimpleHomeDto(home);
//
//        //then
//        Assertions.assertThat(simpleHomeDto.getLatitude()).isEqualTo(-33.33);
//        Assertions.assertThat(simpleHomeDto.getLongitude()).isEqualTo(150.11);
//    }
//
//    private HomeGeneratorRequest generateHomeDto() {
//        HomeAddressGeneratorRequest addressDto = HomeAddressGeneratorRequest.builder()
//                .city("test city")
//                .postCode(1234)
//                .build();
//
//        List<String> images = new ArrayList<>();
//
//        images.add("url1");
//        images.add("url2");
//        images.add("url3");
//
//        HomeGeneratorRequest homeDto = HomeGeneratorRequest.builder()
//                .homeAddress(addressDto)
//                .userId(1L)
//                .images(images)
//                .bill(20000)
//                .bond(10000)
//                .build();
//
//        return homeDto;
//    }
//
//    private User generateUserEntity() {
//        return User.builder()
//                .nickName("이름")
//                .password("12345")
//                .profileUrl("profuleURL")
//                .id(1L)
//                .build();
//    }
//
//    private HomeUpdateRequest generateHomeUpdateRequest() {
//
//        HomeAddressGeneratorRequest homeAddress = HomeAddressGeneratorRequest.builder()
//                .state("NSW")
//                .city("Change")
//                .postCode(2000)
//                .streetCode("20")
//                .streetName("BridgeStreet")
//                .build();
//
//        return HomeUpdateRequest.builder()
//                .homeId(1L)
//                .homeAddress(homeAddress)
//                .bathRoomCount(3)
//                .bedroomCount(10)
//                .build();
//    }
//
//
//    private Home generateHomeEntity() {
//        HomeAddress homeAddress = HomeAddress.builder()
//                .id(1L)
//                .latitude(-33.33)
//                .longitude(150.11)
//                .state("NSW")
//                .city("Sydney")
//                .postCode(2000)
//                .streetCode("10")
//                .streetName("BridgeStreet")
//                .build();
//
//        Home home = Home.builder()
//                .id(1L)
//                .userId(1L)
//                .bathRoomCount(1)
//                .bedroomCount(1)
//                .homeAddress(homeAddress)
//                .build();
//
//        List<HomeImage> images = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            images.add(HomeImage.builder()
//                    .id((long) i)
//                    .imageUrl("url" + i)
//                    .home(home)
//                    .build());
//        }
//
//
//        home.setImages(images);
//
//        return home;
//    }
//
//
//}
