package com.service.point;

import com.common.utils.OptionalUtil;
import com.core.api_core.user.model.ChargeType;
import com.core.api_core.user.model.User;
import com.core.api_core.user.model.UserAccount;
import com.core.api_core.user.repository.UserAccountRepository;
import com.core.api_core.user.repository.UserRepository;
import com.core.exception.InsufficientPointsException;
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
    public double withdraw(String email, int amount) throws InsufficientPointsException  {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIT_USER_EMAIL);
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(user.getId()), NOT_EXIT_USER_ID);
        userAccount.validatePointsSufficiency(amount);
        userAccount.decreasePoint(amount);
        userAccount.registerPointChargeHistory(userAccount.getPoint(), ChargeType.WITHDRAW);
        return userAccount.getPoint();
    }

    //포인트 출금 신청
    public void applyWithDraw(String email, Integer point) throws InsufficientPointsException {

        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIT_USER_EMAIL);
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(user.getId()), NOT_EXIT_USER_ID);
        userAccount.validatePointsSufficiency(point);
        userAccount.registerPointChargeHistory(point, ChargeType.APPLY_WITHDRAW);
        userAccount.decreasePoint(point);
    }


    //포인트 충전
    public void chargePoint(String email, double point) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIT_USER_EMAIL);
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(user.getId()), NOT_EXIT_USER_ID);
        userAccount.increasePoint(point);
        //입금 내역 저장
        userAccount.registerPointChargeHistory(point, ChargeType.DEPOSIT);
    }


}
