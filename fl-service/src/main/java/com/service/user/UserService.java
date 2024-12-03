package com.service.user;

import com.common.user.mapper.UserMapper;
import com.common.user.request.GoogleAuthRequest;
import com.common.user.request.UserAccountRequest;
import com.common.user.request.UserProfileUpdateRequest;
import com.common.user.request.UserSignupRequest;
import com.common.user.response.UserAccountResponse;
import com.common.user.response.UserInformationResponse;
import com.common.user.response.UserProfileResponse;
import com.core.api_core.user.model.ChargeType;
import com.core.api_core.user.model.User;
import com.core.api_core.user.model.UserAccount;
import com.core.api_core.user.repository.UserAccountRepository;
import com.core.api_core.user.repository.UserRepository;
import com.service.file.FileService;
import com.service.user.validation.UserServiceValidation;
import com.common.utils.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

import static com.service.user.UserMessages.*;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final FileService fileService;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserServiceValidation validation;
    private final PasswordEncoder passwordEncoder;

    /**
     * 이메일 회원가입 메서드
     */
    public Long signUp(UserSignupRequest dto, MultipartFile image) throws Exception {
        validation.validateSignUp(dto.getEmail(), dto.getNickname());
        User user = createUser(dto, image);
        encodeAndSetPassword(user, dto.getPassword());
        return userRepository.save(user).getId();
    }

    /**
     * 구글 계정 회원가입 메서드
     */
    public Long signUpGoogleAccount(UserSignupRequest dto, MultipartFile image) throws Exception {
        validation.validateSignUp(dto.getEmail(), dto.getNickname());
        User user = createUser(dto, image);
        return userRepository.save(user).getId();
    }


    /**
     * 이메일 중복 확인 메서드
     */
    public boolean validateDuplicateEmail(String email){
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isEmpty()){
            return false;
        }
        return true;
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

    /**
     * 사용자 프로필 조회 메서드 by userId
     */
    public UserProfileResponse getUserProfile(Long id) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(id), NOT_EXIT_USER_ID);
        return userMapper.toProfile(user);
    }

    /**
     * 사용자 프로필 수정
     */
    @Transactional
    public void update(UserProfileUpdateRequest userProfileUpdateRequest){
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(userProfileUpdateRequest.getUserId()), NOT_EXIT_USER_ID);
        userMapper.updateUser(userProfileUpdateRequest, user);
        userRepository.save(user);
    }

    /**
     * 사용자 프로필 이미지 수정
     */
    @Transactional
    public void updateImage(Long userId, MultipartFile image){
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(userId), NOT_EXIT_USER_ID);
        String profileUrl = uploadProfileImage(image);
        user.setProfileUrl(profileUrl);
    }

    /**
     * 계정 삭제 메서드 by userId
     */
    public void delete(Long id) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(id), NOT_EXIT_USER_ID);
        userRepository.delete(user);
    }

    /**
     * 사용자 계좌 등록
     */
    public void setUserAccount(UserAccountRequest userAccountRequest, Long userId) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(userId), NOT_EXIT_USER_ID);
        UserAccount userAccount = userMapper.toUserAccount(userAccountRequest, user.getId());
        userAccountRepository.save(userAccount);
    }

    /**
     * 사용자 계좌 등록 여부 확인 메서드
     */
    public boolean isExistAccount(Long userId){
        Optional<UserAccount> userAccount = userAccountRepository.findByUserId(userId);
        if(userAccount.isEmpty()){
            return  false;
        }
        return true;
    }

    /**
     * 사용자 계좌 정보 조회
     */
    public UserAccountResponse findUserAccountById(Long userId) {
        //todo 날짜순 정렬
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(userId), NOT_EXIT_USER_ID);
        return userMapper.toUserAccountResponse(userAccount);
    }

    public boolean isExistAccountByEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    private User createUser(UserSignupRequest dto, MultipartFile image) throws Exception {
        User user = userMapper.toEntity(dto);
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

    public void updateAccount(UserAccountRequest userAccountRequest, Long userId) {
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(userId), NOT_EXIT_USER_ID);
        userMapper.updateUserAccount(userAccountRequest, userAccount);
    }
}
