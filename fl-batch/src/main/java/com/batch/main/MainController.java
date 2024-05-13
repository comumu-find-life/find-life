package com.batch.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        log.info("get : /");
        return "main/mainPage";
    }

    @GetMapping("/main")
    public String mainPage2() {
        log.info("get : /main");
        return "main/mainPage";
    }
}
