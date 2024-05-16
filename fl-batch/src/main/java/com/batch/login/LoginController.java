package com.batch.login;

import com.service.home.dto.SimpleHomeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {

    @Value("${domain.api}")
    private String baseUrl;

    @GetMapping("/login")
    public String loginPage() {
        return "login/login";
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginFormDto loginFormDto) {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/login";

        SimpleHomeDto[] homeDtos = restTemplate.getForObject(url, SimpleHomeDto[].class);
    }
}
