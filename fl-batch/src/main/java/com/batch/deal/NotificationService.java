package com.batch.deal;

import com.common.fcm.FCMHelper;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.batch.deal.FcmMessages.*;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final FCMHelper fcmHelper;
    private final UserRepository userRepository;

    public void sendCompleteDealNotification(ProtectedDeal protectedDeal) throws IllegalAccessException {
        User getter = userRepository.findById(protectedDeal.getGetterId())
                .orElseThrow(() -> new IllegalArgumentException("Getter user not found"));

        User provider = userRepository.findById(protectedDeal.getProviderId())
                .orElseThrow(() -> new IllegalArgumentException("Provider user not found"));

        sendNotification(getter);
        sendNotification(provider);
    }

    public void sendAutoCompleteDealNotification(String fcmToken) throws IllegalAccessException {
        fcmHelper.sendNotification(fcmToken, COMPLETE_DEAL_TITLE, COMPLETE_DEAL_BODY);
    }

    private void sendNotification(User user) throws IllegalAccessException {
        fcmHelper.sendNotification(user.getFcmToken(), TODAY_DEAL_TITLE_MESSAGE, TODAY_DEAL_BODY_MESSAGE);
    }
}