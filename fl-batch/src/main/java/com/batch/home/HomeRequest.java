package com.batch.home;

import com.common.home.request.HomeAddressGeneratorRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HomeRequest {

    private Long id;

    private HomeAddressGeneratorRequest homeAddress;

    private Integer rent;
    private Integer bond;
    private Integer bill;

    private Integer bedroomCount;
    private Integer bathroomCount;
    private Integer tenantCount;

    private String introduce;
    private String shortIntroduce;

    private String gender;
    private String Type;
    private String pictures;

}
