package com.service.user;

import com.common.image.FileService;
import com.common.user.mapper.UserMapper;
import com.common.user.request.UserAccountRequest;
import com.common.user.request.UserProfileUpdateRequest;
import com.common.user.request.UserSignupRequest;
import com.common.user.response.UserAccountResponse;
import com.common.user.response.UserInformationResponse;
import com.common.user.response.UserProfileResponse;
import com.common.user.response.WithDrawHistoryResponse;
import com.core.api_core.user.model.ChargeType;
import com.core.api_core.user.model.PointHistory;
import com.core.api_core.user.model.User;
import com.core.api_core.user.model.UserAccount;
import com.core.api_core.user.repository.UserAccountRepository;
import com.core.api_core.user.repository.UserRepository;
import com.service.user.validation.UserServiceValidation;
import com.common.utils.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Value("${admin.token}")
    private String secretToken;

    /**
     * 이메일 회원가입 메서드
     */
    public Long signUp(UserSignupRequest dto,String encodePassword, MultipartFile image) throws Exception {
        validation.validateSignUp(dto.getEmail(), dto.getNickname());
        User user = createUser(dto, encodePassword, image);
        //encodeAndSetPassword(user, dto.getPassword());
        return userRepository.save(user).getId();
    }

    /**
     * 이메일 중복 확인 메서드
     */
    public boolean validateDuplicateEmail(String email){
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.isEmpty();
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
     * 사용자 계좌 정보 수정
     */
    public void updateAccount(UserAccountRequest userAccountRequest, String email) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIT_USER_EMAIL);
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(user.getId()), NOT_EXIT_USER_ID);
        userMapper.updateUserAccount(userAccountRequest, userAccount);
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

    @Transactional
    public void updateRefreshToken(String email, String refreshToken){
        userRepository.findByEmail(email).get().setRefreshToken(refreshToken);
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
        return !userAccount.isEmpty();
    }

    /**
     * 사용자 계좌 정보 조회
     */
    public UserAccountResponse findUserAccountById(Long userId) {
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(userId), NOT_EXIT_USER_ID);
        return userMapper.toUserAccountResponse(userAccount);
    }

    public boolean isExistAccountByEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    private User createUser(UserSignupRequest dto, String password,MultipartFile image) throws Exception {
        User user = userMapper.toEntity(dto);
        user.passwordEncode(password);
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


    @Transactional
    public void updateFcmToken(final String email, final String fcmToken) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIT_USER_EMAIL);
        user.setFcmToken(fcmToken);

    }

    /**
     * 출금 신청된 정보 조회 메서드 (by admin)
     */
    public List<WithDrawHistoryResponse> findWithDraws() {
        return userAccountRepository.findAll().stream()
                .flatMap(userAccount -> userAccount.getChargeHistories().stream()
                        .filter(pointHistory -> pointHistory.getChargeType().equals(ChargeType.APPLY_WITHDRAW))
                        .map(pointHistory -> WithDrawHistoryResponse.builder()
                                .userAccountId(userAccount.getUserId())
                                .pointHistoryId(pointHistory.getId())
                                .paypalInformation(userAccount.getPaypalInformation())
                                .chargeAmount(pointHistory.getChargeAmount())
                                .chargeType(pointHistory.getChargeType())
                                .depositorName(userAccount.getDepositorName())
                                .historyDateTime(pointHistory.getHistoryDateTime())
                                .build()))
                .collect(Collectors.toList());
    }

    /**
     * 환전 완료
     */
    @Transactional
    public void completeWithDraw(Long userAccountId, Long pointHistoryId, String token){
        if(!token.equals(secretToken)){
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(userAccountId), NOT_EXIT_USER_ID);
        PointHistory pointHistory = userAccount.getChargeHistories().stream()
                .filter(history -> history.getId() == pointHistoryId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
        pointHistory.setChargeType(ChargeType.WITHDRAW);
        //fcm 알림 기능 구현
    }
}
