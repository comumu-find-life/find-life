package com.admin.user;

import com.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;

    @GetMapping
    public String list(final HttpSession session, final Model model) {
        model.addAttribute("withdraws", userService.findWithDraws());
        return "withdraw/list";
    }

    @PostMapping("/complete")
    public String completeWithdraw(
            @RequestParam final Long userAccountId,
            @RequestParam final Long pointHistoryId,
            @RequestParam final String token,
            Model model) {
        try {
            userService.completeWithDraw(userAccountId, pointHistoryId, token);
            model.addAttribute("successMessage", "환전 처리가 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "환전 처리 실패: " + e.getMessage());
        }
        model.addAttribute("withdraws", userService.findWithDraws());
        return "withdraw/list";
    }

}