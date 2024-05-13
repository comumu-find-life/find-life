package com.service.job.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class JobCreateDto {

    private Long userId;

    private List<String> images;

    private String storeName;

    private Integer salary;


}
