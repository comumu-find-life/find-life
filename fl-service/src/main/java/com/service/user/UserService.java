package com.service.user;

import com.common.user.mapper.UserMapper;
import com.common.user.request.UserSignupRequest;
import com.common.user.response.UserInformationResponse;
import com.common.user.response.UserProfileResponse;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.service.file.FileService;
import com.service.user.validation.UserServiceValidation;
import com.common.utils.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.service.user.UserMessages.*;


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

    public boolean validateDuplicateEmail(String email){
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * 회원가입시 인증 코드 전송 메서드
     */
    public void sendCheckCodeToEmail(String email){

    }

    /**
     * 회원 조회 메서드 by userId
     */
    public UserInformationResponse findById(Long id) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(id), NOT_EXIT_USER_ID);
        return userMapper.toDto(user);
    }

    /**
     * 회원 조회 메서드 by email
     */
    public UserInformationResponse findByEmail(String email) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIT_USER_EMAIL);
        return userMapper.toDto(user);
    }



    public Long findUserIdByEmail() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(currentUsername), NOT_EXIT_USER_NICKNAME);
        return user.getId();
    }


    /**
     * 사용자 프로필 조회 메서드 by userId
     */
    public UserProfileResponse getUserProfile(Long id) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(id), NOT_EXIT_USER_ID);
        return userMapper.toProfile(user);
    }

    /**
     * 계정 삭제 메서드 by userId
     */
    public void delete(Long id) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(id), NOT_EXIT_USER_ID);
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
