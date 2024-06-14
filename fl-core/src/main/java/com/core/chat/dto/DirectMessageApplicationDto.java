package com.core.chat.dto;


import lombok.Getter;

/**
 * DTO COMMON 으로 이동
 */
@Getter
public class DirectMessageApplicationDto {

    private String message;
    private Long receiverId;
    private Long roomId;
}
