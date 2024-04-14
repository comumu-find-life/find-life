package com.service.home.dto;

import lombok.Builder;

@Builder
public class SimpleHomeDto {
    private String address;
    private String imageUrl;
    private Integer bond;
    private Integer bill;
}