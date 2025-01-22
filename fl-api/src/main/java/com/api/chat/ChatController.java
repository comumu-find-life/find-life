package com.api.chat;

import com.core.api_core.chat.dto.DirectMessageTotalRequest;
import com.core.api_core.chat.dto.DirectMessageTotalResponse;
import com.core.api_core.deal.dto.ProtectedDealResponse;
import com.core.api_core.home.dto.HomeInformationResponse;
import com.core.api_core.user.dto.UserProfileResponse;
import com.common.utils.SuccessResponse;
import com.service.deal.ProtectedDealService;
import com.service.home.HomeQueryService;
import com.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.api.config.ApiUrlConstants.CHAT_TOTAL_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ChatController {
    private final UserService userService;
    private final HomeQueryService homeQueryService;
    private final ProtectedDealService protectedDealService;



    @PostMapping(CHAT_TOTAL_URL)
    public ResponseEntity<?> getDirectMessageTotalResponse(@RequestBody final DirectMessageTotalRequest directMessageTotalRequest) {
        long startTime = System.currentTimeMillis(); // 시작 시간 기


        // senderReceiver 조회
        List<UserProfileResponse> senderReceiver = userService.findSenderReceiver(directMessageTotalRequest.getSenderId(), directMessageTotalRequest.getReceiverId());
        long senderReceiverTime = System.currentTimeMillis(); // 종료 시간 기록
        System.out.println("findSenderReceiver 실행 시간: " + (senderReceiverTime - startTime) + "ms");

        UserProfileResponse sender = senderReceiver.get(0);
        UserProfileResponse receiver = senderReceiver.get(1);

        HomeInformationResponse homeInformationResponse = homeQueryService.findById(directMessageTotalRequest.getHomeId());
        long homeInfoTime = System.currentTimeMillis(); // 종료 시간 기록
        System.out.println("findById 실행 시간: " + (homeInfoTime - senderReceiverTime) + "ms");

        Long getterId;
        Long providerId;
        if (Long.valueOf(homeInformationResponse.getProviderId()) == sender.getId()) {
            getterId = receiver.getId();
            providerId = sender.getId();
        } else {
            providerId = receiver.getId();
            getterId = sender.getId();
        }

        // isExistAccount 조회
        boolean isExist = userService.isExistAccount(sender.getId());
        long isExistTime = System.currentTimeMillis(); // 종료 시간 기록
        System.out.println("isExistAccount 실행 시간: " + (isExistTime - homeInfoTime) + "ms");

        // protectedDeal 조회
        List<ProtectedDealResponse> protectedDeal = protectedDealService.findProtectedDeal(getterId, providerId, directMessageTotalRequest.getHomeId(), directMessageTotalRequest.getRoomId());
        long protectedDealTime = System.currentTimeMillis(); // 종료 시간 기록
        System.out.println("findProtectedDeal 실행 시간: " + (protectedDealTime - isExistTime) + "ms");

        DirectMessageTotalResponse directMessageTotalResponse = DirectMessageTotalResponse.builder()
                .sender(sender)
                .receiver(receiver)
                .homeInformationResponse(homeInformationResponse)
                .isExistAccount(isExist)
                .protectedDealResponse(protectedDeal)
                .build();

        SuccessResponse response = new SuccessResponse(true, "채팅방 정보조회 성공", directMessageTotalResponse);

        long endTime = System.currentTimeMillis(); // 최종 종료 시간
        System.out.println("전체 처리 시간: " + (endTime - startTime) + "ms");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
