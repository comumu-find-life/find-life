package com.common.fcm;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FCMHelper {


    private final FirebaseMessaging firebaseMessaging;

    public void sendNotification(String token, String title, String body) throws IllegalAccessException {
        try {
            Message message = createMessage(token, title, body);
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException ex) {
            System.out.println("ex =" + ex.getMessage());
            throw new IllegalAccessException("fcm 전송 실패");
        }
    }

    private Message createMessage(String token, String title, String body){
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
                                .setContentAvailable(true)
                                .build()
                        )
                        .build()
                )
                .putData("title", title) // Android 데이터 추가
                .putData("body", body)
                .build();
    }

}
