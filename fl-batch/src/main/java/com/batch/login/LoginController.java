package com.batch.login;

import com.service.home.dto.SimpleHomeDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {

    @Value("${domain.api}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/login")
    public String loginPage() {
        return "login/login";
    }

    @PostMapping("/login")
    public String login(LoginFormDto loginFormDto, HttpServletResponse response) {
        String url ="http://localhost:8080/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginFormDto> requestEntity = new HttpEntity<>(loginFormDto, headers);

        try {
            JwtTokenDto tokenDto = restTemplate.postForObject(url, requestEntity, JwtTokenDto.class);

            Cookie cookie = new Cookie("Authorization", tokenDto.getAccessToken());
            cookie.setPath("/");
            cookie.setHttpOnly(true); // 서버만 쿠키에 접근
            cookie.setSecure(false);
            response.addCookie(cookie);
        } catch (Exception e) {
            return "login/login";
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie cookie = new Cookie("Authorization", null);

        // 쿠키 경로 설정 (필요에 따라 변경 가능)
        cookie.setPath("/");

        // 쿠키 유효 시간 설정 (0으로 설정하여 즉시 만료)
        cookie.setMaxAge(0);

        // 응답에 쿠키 추가하여 클라이언트로 전송
        response.addCookie(cookie);

        return "redirect:/";
    }
}
