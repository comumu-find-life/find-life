package com.service.user;

import com.common.user.mapper.UserMapper;
import com.common.user.request.UserSignupRequest;
import com.common.user.response.UserInformationResponse;
import com.common.user.response.UserProfileResponse;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.service.file.FileService;
import com.service.user.validation.UserServiceValidation;
import com.service.utils.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final FileService fileService;
    private final UserRepository userRepository;
    private final UserServiceValidation validation;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 메서드
     */
    public Long signUp(UserSignupRequest dto, MultipartFile image) throws Exception {
        // 검증
        validation.validateSignUp(dto.getEmail(), dto.getNickname());

        // User 객체 생성
        User user = createUser(dto, image);

        // 비밀번호 인코딩 및 설정
        encodeAndSetPassword(user, dto.getPassword());

        // 사용자 저장 및 ID 반환
        return userRepository.save(user).getId();
    }
    /**
     * 회원 조회 메서드 by userId
     */
    public UserInformationResponse findById(Long id) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(id), "존재하지 않는 user ID 입니다.");
        return userMapper.toDto(user);
    }

    /**
     * 회원 조회 메서드 by email
     */
    public UserInformationResponse findByEmail(String email) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), "존재하지 않는 user email 입니다.");
        return userMapper.toDto(user);
    }


    /**
     * 사용자 프로필 조회 메서드 by userId
     */
    public UserProfileResponse getUserProfile(Long id) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(id), "존재하지 않는 user ID 입니다.");
        return userMapper.toProfile(user);
    }

    /**
     * 계정 삭제 메서드 by userId
     */
    public void delete(Long id) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(id), "존재하지 않는 user ID 입니다.");
        userRepository.delete(user);
    }

    private User createUser(UserSignupRequest dto, MultipartFile image) throws Exception {
        User user = userMapper.toEntity(dto);
        // 프로필 사진이 있을 때 프로필 URL 설정
        if (image != null) {
            String profileUrl = uploadProfileImage(image);
            user.setProfileUrl(profileUrl);
        }

        return user;
    }

    private String uploadProfileImage(MultipartFile image)  {
        String url = fileService.toUrls(image);
        fileService.fileUpload(image, url);
        return url;
    }

    private void encodeAndSetPassword(User user, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        user.passwordEncode(encodedPassword);
    }

}
