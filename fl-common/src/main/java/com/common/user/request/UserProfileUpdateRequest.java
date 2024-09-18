package com.common.user.request;

import com.core.api_core.user.model.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 프로필 정보 수정 DTO
 */
@Getter
@Setter
@Builder
public class UserProfileUpdateRequest {

    private Long userId;

    private String nickname;

    private String job;

    private Gender gender;

    private String introduce;
}
