package com.batch.chat;

import com.core.chat.dto.DirectMessageRoomInfoDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/dm")
    public String dmRoomList() {
        return "chat/dmRoomList";
    }

    @GetMapping("/dm/{id}")
    public String dmRoom(@PathVariable Long id, Model model, HttpServletRequest request) {
        DirectMessageRoomInfoDto dmRoomInfo = chatService.findDmRoomInfoByRoomId((String) request.getAttribute("accessToken"), id);
        model.addAttribute("roomId", id);
        model.addAttribute("dmRoomInfo", dmRoomInfo);

        return "chat/dmRoomList";
    }
}
