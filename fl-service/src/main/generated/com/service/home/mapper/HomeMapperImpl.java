package com.service.home.mapper;

import com.core.home.model.Home;
import com.core.home.model.HomeAddress;
import com.core.user.model.User;
import com.service.home.dto.HomeOverviewResponse;
import com.service.home.dto.request.HomeAddressGeneratorRequest;
import com.service.home.dto.request.HomeGeneratorRequest;
import com.service.home.dto.response.HomeInformationResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-26T22:39:23+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class HomeMapperImpl implements HomeMapper {

    @Override
    public Home toEntity(HomeGeneratorRequest homeDto) {
        if ( homeDto == null ) {
            return null;
        }

        Home.HomeBuilder home = Home.builder();

        home.userId( homeDto.getUserId() );
        home.homeAddress( toAddressEntity( homeDto.getHomeAddress() ) );
        home.bathRoomCount( homeDto.getBathRoomCount() );
        home.bedroomCount( homeDto.getBedroomCount() );
        home.dealSavable( homeDto.isDealSavable() );
        home.options( homeDto.getOptions() );
        home.bond( homeDto.getBond() );
        home.gender( homeDto.getGender() );
        home.type( homeDto.getType() );
        home.introduce( homeDto.getIntroduce() );
        home.bill( homeDto.getBill() );
        home.rent( homeDto.getRent() );

        home.status( com.core.home.model.HomeStatus.FOR_SALE );

        return home.build();
    }

    @Override
    public HomeAddress toAddressEntity(HomeAddressGeneratorRequest homeAddressDto) {
        if ( homeAddressDto == null ) {
            return null;
        }

        HomeAddress.HomeAddressBuilder homeAddress = HomeAddress.builder();

        homeAddress.state( homeAddressDto.getState() );
        homeAddress.city( homeAddressDto.getCity() );
        homeAddress.postCode( homeAddressDto.getPostCode() );
        homeAddress.detailAddress( homeAddressDto.getDetailAddress() );
        homeAddress.streetName( homeAddressDto.getStreetName() );
        homeAddress.streetCode( homeAddressDto.getStreetCode() );

        return homeAddress.build();
    }

    @Override
    public HomeInformationResponse toHomeInformation(Home home, User user) {
        if ( home == null && user == null ) {
            return null;
        }

        HomeInformationResponse.HomeInformationResponseBuilder homeInformationResponse = HomeInformationResponse.builder();

        if ( home != null ) {
            homeInformationResponse.address( mapSimpleAddress( home.getHomeAddress() ) );
            homeInformationResponse.images( mapImageUrls( home.getImages() ) );
            homeInformationResponse.bill( home.getBill() );
            homeInformationResponse.rent( home.getRent() );
            homeInformationResponse.type( home.getType() );
            homeInformationResponse.introduce( home.getIntroduce() );
            homeInformationResponse.bathRoomCount( home.getBathRoomCount() );
            homeInformationResponse.bedroomCount( home.getBedroomCount() );
        }
        if ( user != null ) {
            if ( user.getId() != null ) {
                homeInformationResponse.providerId( String.valueOf( user.getId() ) );
            }
            homeInformationResponse.providerProfileUrl( user.getProfileUrl() );
            homeInformationResponse.providerName( user.getNickName() );
        }

        return homeInformationResponse.build();
    }

    @Override
    public HomeOverviewResponse toSimpleHomeDto(Home home) {
        if ( home == null ) {
            return null;
        }

        HomeOverviewResponse.HomeOverviewResponseBuilder homeOverviewResponse = HomeOverviewResponse.builder();

        homeOverviewResponse.address( mapSimpleAddress( home.getHomeAddress() ) );
        homeOverviewResponse.latitude( homeHomeAddressLatitude( home ) );
        homeOverviewResponse.longitude( homeHomeAddressLongitude( home ) );
        homeOverviewResponse.mainImage( mapMainImage( home.getImages() ) );
        homeOverviewResponse.id( home.getId() );
        homeOverviewResponse.rent( home.getRent() );
        homeOverviewResponse.bond( home.getBond() );
        homeOverviewResponse.bill( home.getBill() );
        homeOverviewResponse.bedroomCount( home.getBedroomCount() );
        homeOverviewResponse.bathRoomCount( home.getBathRoomCount() );
        if ( home.getType() != null ) {
            homeOverviewResponse.type( home.getType().name() );
        }

        return homeOverviewResponse.build();
    }

    @Override
    public HomeAddressGeneratorRequest toAddressDto(HomeAddress address) {
        if ( address == null ) {
            return null;
        }

        HomeAddressGeneratorRequest.HomeAddressGeneratorRequestBuilder homeAddressGeneratorRequest = HomeAddressGeneratorRequest.builder();

        homeAddressGeneratorRequest.state( address.getState() );
        homeAddressGeneratorRequest.city( address.getCity() );
        homeAddressGeneratorRequest.postCode( address.getPostCode() );
        homeAddressGeneratorRequest.detailAddress( address.getDetailAddress() );
        homeAddressGeneratorRequest.streetName( address.getStreetName() );
        homeAddressGeneratorRequest.streetCode( address.getStreetCode() );

        return homeAddressGeneratorRequest.build();
    }

    private double homeHomeAddressLatitude(Home home) {
        if ( home == null ) {
            return 0.0d;
        }
        HomeAddress homeAddress = home.getHomeAddress();
        if ( homeAddress == null ) {
            return 0.0d;
        }
        double latitude = homeAddress.getLatitude();
        return latitude;
    }

    private double homeHomeAddressLongitude(Home home) {
        if ( home == null ) {
            return 0.0d;
        }
        HomeAddress homeAddress = home.getHomeAddress();
        if ( homeAddress == null ) {
            return 0.0d;
        }
        double longitude = homeAddress.getLongitude();
        return longitude;
    }
}
