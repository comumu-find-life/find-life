package com.batch.deal;

import com.infra.fcm.FCMHelper;
import com.infra.fcm.FCMState;
import com.infra.utils.OptionalUtil;
import com.core.domain.deal.model.ProtectedDeal;
import com.core.domain.user.model.User;
import com.core.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.batch.deal.FcmMessages.*;
import static com.infra.exception.ExceptionMessages.NOT_EXIST_USER_ID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final FCMHelper fcmHelper;
    private final UserRepository userRepository;

    public void sendCompleteDealNotification(final ProtectedDeal protectedDeal)  {
        User getter = OptionalUtil.getOrElseThrow(userRepository.findById(protectedDeal.getGetterId()), NOT_EXIST_USER_ID);
        User provider = OptionalUtil.getOrElseThrow(userRepository.findById(protectedDeal.getProviderId()), NOT_EXIST_USER_ID);
        sendNotification(getter);
        sendNotification(provider);
    }

    public void sendAutoCompleteDealNotification(final String fcmToken) {
        fcmHelper.sendNotification(FCMState.SAVE, fcmToken, COMPLETE_DEAL_TITLE, COMPLETE_DEAL_BODY);
    }

    private void sendNotification(final User user)  {
        fcmHelper.sendNotification(FCMState.SAVE, user.getFcmToken(), TODAY_DEAL_TITLE_MESSAGE, TODAY_DEAL_BODY_MESSAGE);
    }
}