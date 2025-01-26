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

import static com.core.exception.ExceptionMessages.NOT_EXIST_USER_EMAIL;
import static com.core.exception.ExceptionMessages.NOT_EXIST_USER_ID;

@Transactional
@Service
@RequiredArgsConstructor
public class PointService {

    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    public void applyWithDraw(final String email, final double point) throws InsufficientPointsException {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIST_USER_EMAIL);
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(user.getId()), NOT_EXIST_USER_ID);
        userAccount.validatePointsSufficiency(point);
        userAccount.registerPointChargeHistory(point, ChargeType.APPLY_WITHDRAW);
        userAccount.decreasePoint(point);
    }


    public void chargePoint(final String email, final double point) {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIST_USER_EMAIL);
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(user.getId()), NOT_EXIST_USER_ID);
        userAccount.increasePoint(point);
        userAccount.registerPointChargeHistory(point, ChargeType.DEPOSIT);
    }
}
