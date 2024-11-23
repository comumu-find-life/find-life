package com.service.point;

import com.common.utils.OptionalUtil;
import com.core.api_core.user.model.ChargeType;
import com.core.api_core.user.model.User;
import com.core.api_core.user.model.UserAccount;
import com.core.api_core.user.repository.UserAccountRepository;
import com.core.api_core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.service.user.UserMessages.NOT_EXIT_USER_EMAIL;
import static com.service.user.UserMessages.NOT_EXIT_USER_ID;

@Transactional
@Service
@RequiredArgsConstructor
public class PointService {

    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    //포인트 출금
    public Integer withdraw(String email, int amount) throws IllegalAccessException {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIT_USER_EMAIL);
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(user.getId()), NOT_EXIT_USER_ID);
        userAccount.validatePointsSufficiency(amount);
        userAccount.decreasePoint(amount);
        //TODO 송금 로직 구현

        userAccount.registerPointChargeHistory(userAccount.getPoint(), ChargeType.WITHDRAW);
        return userAccount.getPoint();
    }

    //포인트 출금 신청
    public void applyWithDraw(String email, Integer point){
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIT_USER_EMAIL);
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(user.getId()), NOT_EXIT_USER_ID);
        userAccount.registerPointChargeHistory(point, ChargeType.APPLY_WITHDRAW);
    }

    // 계좌 송금 후 입금 신청 (by getter)
    public void applyDepositByAccount(String email, Integer point){
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIT_USER_EMAIL);
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(user.getId()), NOT_EXIT_USER_ID);
        userAccount.registerPointChargeHistory(point, ChargeType.APPLY_DEPOSIT);
    }

    //포인트 충전
    public void chargePoint(String email, Integer point) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIT_USER_EMAIL);
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(user.getId()), NOT_EXIT_USER_ID);
        userAccount.setPoint(point);
        userAccount.registerPointChargeHistory(point, ChargeType.DEPOSIT);
    }


}
