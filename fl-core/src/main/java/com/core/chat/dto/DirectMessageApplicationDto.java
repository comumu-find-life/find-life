package com.core.chat.dto;


import lombok.Getter;

@Getter
public class DirectMessageApplicationDto {

    private String message;
    private Long receiverId;
    private Long roomId;
}
