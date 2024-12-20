package com.service.deal;

import com.common.deal.mapper.ProtectedDealMapper;
import com.common.deal.request.ProtectedDealFindRequest;
import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.common.deal.response.MyProtectedDealResponse;
import com.common.deal.response.ProtectedDealGeneratorResponse;
import com.common.deal.response.ProtectedDealResponse;
import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.repository.ProtectedDealRepository;
import com.core.api_core.home.model.Home;
import com.core.api_core.home.repository.HomeRepository;
import com.common.utils.OptionalUtil;
import com.core.api_core.user.model.User;
import com.core.api_core.user.model.UserAccount;
import com.core.api_core.user.repository.UserAccountRepository;
import com.core.api_core.user.repository.UserRepository;
import com.service.utils.SecretKeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.service.deal.ProtectedDealMessages.DEAL_NOT_FOUND;
import static com.service.home.HomeMessages.NOT_EXIST_HOME_ID;
import static com.service.user.UserMessages.NOT_EXIT_USER_ID;
import static com.service.utils.SecretKeyUtil.generateSecretKey;

@Transactional
@Service
@RequiredArgsConstructor
public class ProtectedDealService {

    private final ProtectedDealRepository protectedDealRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserRepository userRepository;
    private final HomeRepository homeRepository;
    private final ProtectedDealMapper mapper;

    /**
     * 안전 거래 생성 요청 메서드 (by provider)
     */
    public ProtectedDealGeneratorResponse requestProtectedDeal(ProtectedDealGeneratorRequest request) throws Exception {
        // SecretKey 생성
        String secretKey = generateSecretKey();
        ProtectedDeal deal = mapper.toEntity(request, secretKey);
        Long dealId = protectedDealRepository.save(deal).getId();
        User provider = OptionalUtil.getOrElseThrow(userRepository.findById(request.getProviderId()), NOT_EXIT_USER_ID);
        String encryptedSecretKey = SecretKeyUtil.encrypt(secretKey, provider.getEmail());
        ProtectedDealGeneratorResponse protectedDealGeneratorResponse = mapper.toGeneratorResponse(dealId, encryptedSecretKey);
        return protectedDealGeneratorResponse;
    }

    /**
     * 내 안전 거래 조회 메서드
     */
    public List<MyProtectedDealResponse> findAllByUserId(Long userId) {
        List<ProtectedDeal> allByUserId = protectedDealRepository.findAllByUserId(userId);
        List<MyProtectedDealResponse> response = new ArrayList<>();
        allByUserId.stream()
                .forEach(protectedDeal -> {
                    Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(protectedDeal.getHomeId()), NOT_EXIST_HOME_ID);
                    response.add(mapper.toMyProtectedDealResponse(protectedDeal, home));
                });
        return response;
    }

    /**
     * 안전거래 조회 메서드 by Getter
     */
    public List<ProtectedDealResponse> findProtectedDeal(ProtectedDealFindRequest request) {
        List<ProtectedDealResponse> responses = new ArrayList<>();
        List<ProtectedDeal> protectedDeals = protectedDealRepository.findByMultipleParams(request.getGetterId(), request.getProviderId(), request.getHomeId(), request.getDmId());
        protectedDeals.stream()
                .forEach(protectedDeal -> {
                    Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(protectedDeal.getHomeId()), NOT_EXIST_HOME_ID);
                    responses.add(mapper.toGetterResponse(protectedDeal, home));
                });
        return responses;
    }


    /**
     * 안전 거래 수락 by getter 암호키 반환
     */
    @Transactional
    public String acceptProtectedDeal(Long dealId) throws Exception {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), DEAL_NOT_FOUND);
        User getter = OptionalUtil.getOrElseThrow(userRepository.findById(protectedDeal.getGetterId()), NOT_EXIT_USER_ID);

        UserAccount userAccount = userAccountRepository.findByUserId(getter.getId()).get();
        userAccount.validatePointsSufficiency(protectedDeal.getDeposit());

        userAccount.decreasePoint(protectedDeal.getDeposit());

        protectedDeal.setDealState(DealState.ACCEPT_DEAL);
        protectedDeal.getProtectedDealDateTime().setStartAt(LocalDateTime.now());

        return SecretKeyUtil.encrypt(protectedDeal.getSecretKey(), getter.getEmail());
    }


    /**
     * 거래 완료 메서드 by getter
     */
    @Transactional
    public void requestCompleteDeal(Long dealId, String secretKey) throws Exception {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), DEAL_NOT_FOUND);
        validateGetterSecretKey(protectedDeal, secretKey);
        //집주인(provider) 포인트 증가
        UserAccount userAccount = userAccountRepository.findByUserId(protectedDeal.getProviderId()).get();
        userAccount.increasePoint(protectedDeal.getDeposit());
        protectedDeal.getProtectedDealDateTime().setCompleteAt(LocalDateTime.now());
        protectedDeal.setDealState(DealState.COMPLETE_DEAL);
    }

    /**
     * 안전 거래 생성 전 취소 메서드
     */
    @Transactional
    public void cancelBeforeDeal(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), DEAL_NOT_FOUND);
        protectedDeal.getProtectedDealDateTime().setCancelAt(LocalDateTime.now());
        protectedDeal.setDealState(DealState.CANCEL_BEFORE_DEAL);
    }

    /**
     * 안전 거래 생성 후 취소 메서드 (by getter)
     */
    @Transactional
    public void cancelAfterDeal(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), DEAL_NOT_FOUND);

        //세입자 (getter) 포인트 증가
        UserAccount userAccount = userAccountRepository.findByUserId(protectedDeal.getGetterId()).get();
        userAccount.increasePoint(protectedDeal.getDeposit());

        protectedDeal.getProtectedDealDateTime().setCancelAt(LocalDateTime.now());
        protectedDeal.setDealState(DealState.CANCEL_DURING_DEAL);
    }


    private void validateGetterSecretKey(ProtectedDeal protectedDeal, String secretKey) throws Exception {
        String protectedDealSecretKey = protectedDeal.getSecretKey();
        User getter = OptionalUtil.getOrElseThrow(userRepository.findById(protectedDeal.getGetterId()), NOT_EXIT_USER_ID);
        String cleanedSecretKey = cleanSecretKey(secretKey);
        // 복호화 후 이메일 검증
        String getterEmail = SecretKeyUtil.decrypt(protectedDealSecretKey, cleanedSecretKey);
        if (!getter.getEmail().equals(getterEmail)) {
            throw new IllegalAccessException("올바르지 않은 암호키: 인증 실패");
        }
    }

    // 비밀키를 트림하고 큰따옴표 제거하는 메서드
    private String cleanSecretKey(String secretKey) {
        String trimmedKey = secretKey.trim();
        // 큰따옴표로 감싸져 있으면 제거
        if (trimmedKey.startsWith("\"") && trimmedKey.endsWith("\"")) {
            return trimmedKey.substring(1, trimmedKey.length() - 1);
        }
        return trimmedKey;
    }


}
