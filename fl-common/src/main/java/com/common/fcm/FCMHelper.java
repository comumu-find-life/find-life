package com.common.fcm;

import com.core.exception.FcmException;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FCMHelper {

    private final FirebaseMessaging firebaseMessaging;

    public void sendNotification(FCMState fcmState, String token, String title, String body) {
        try {
            Message message = createMessage(fcmState, token, title, body);
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException ex) {
            throw new FcmException("fcm 전송 실패");
        }
    }

    private Message createMessage(FCMState fcmState, String token, String title, String body) {
        return Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build()
                )
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setAlert(ApsAlert.builder()
                                        .setTitle(title)
                                        .setBody(body)
                                        .build()
                                )
                                .setContentAvailable(true) // iOS 백그라운드 처리를 위한 키 추가
                                .setMutableContent(true)  // iOS 푸시 내용 변경 가능 설정
                                .build()
                        )
                        // iOS용 데이터 추가
                        .putHeader("apns-push-type", "alert") // 푸시 유형 설정 (필수)
                        .putHeader("apns-priority", "10")    // 우선 순위 설정
                        .putCustomData("isSave", fcmState.getValue()) // 데이터 추가
                        .putCustomData("title", title)
                        .putCustomData("body", body)
                        .build()
                )
                // Android용 데이터 추가
                .putData("title", title)
                .putData("body", body)
                .putData("isSave", fcmState.getValue())
                .build();
    }


}
