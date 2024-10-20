package com.service.point;

import com.common.utils.OptionalUtil;
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

    public Integer withdraw(String email, int amount) throws IllegalAccessException {
        User user = OptionalUtil.getOrElseThrow(userRepository.findByEmail(email), NOT_EXIT_USER_EMAIL);
        UserAccount userAccount = OptionalUtil.getOrElseThrow(userAccountRepository.findByUserId(user.getId()), NOT_EXIT_USER_ID);
        userAccount.validatePointsSufficiency(amount);
        userAccount.decreasePoint(amount);
        //TODO 송금 로직 구현

        return userAccount.getPoint();
    }
}
