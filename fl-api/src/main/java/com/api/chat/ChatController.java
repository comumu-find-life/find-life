package com.api.chat;

import com.api.deal.SuccessProtectedDealMessages;
import com.common.chat.request.DirectMessageTotalRequest;
import com.common.chat.response.DirectMessageTotalResponse;
import com.common.deal.response.ProtectedDealResponse;
import com.common.home.response.HomeInformationResponse;
import com.common.user.response.UserProfileResponse;
import com.common.utils.SuccessResponse;
import com.service.deal.ProtectedDealService;
import com.service.home.HomeQueryService;
import com.service.home.HomeService;
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
    public ResponseEntity<?> getDirectMessageTotalResponse(@RequestBody final DirectMessageTotalRequest directMessageTotalRequest){
        UserProfileResponse sender = userService.getUserProfile(directMessageTotalRequest.getSenderId());
        UserProfileResponse receiver = userService.getUserProfile(directMessageTotalRequest.getReceiverId());
        HomeInformationResponse homeInformationResponse = homeQueryService.findById(directMessageTotalRequest.getHomeId());
        Long getterId;
        Long providerId;
        if(Long.valueOf(homeInformationResponse.getProviderId()) == sender.getId()){
            getterId = receiver.getId();
            providerId = sender.getId();
        }else{
            providerId = receiver.getId();
            getterId = sender.getId();
        }
        boolean isExist = userService.isExistAccount(sender.getId());
        List<ProtectedDealResponse> protectedDeal = protectedDealService.findProtectedDeal(getterId, providerId, directMessageTotalRequest.getHomeId(), directMessageTotalRequest.getRoomId());
        DirectMessageTotalResponse directMessageTotalResponse = DirectMessageTotalResponse.builder()
                .sender(sender)
                .receiver(receiver)
                .homeInformationResponse(homeInformationResponse)
                .isExistAccount(isExist)
                .protectedDealResponse(protectedDeal)
                .build();

        SuccessResponse response = new SuccessResponse(true, "채팅방 정보조회 성공", directMessageTotalResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
