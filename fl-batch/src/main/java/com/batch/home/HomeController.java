package com.batch.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/homes")
    public String homeList(@RequestParam String city) {

        return "redirect:/homes/" + city;
    }

    @GetMapping("/homes/{city}")
    public String homeListPage(@PathVariable String city, Model model) {
        model.addAttribute("city", city);
        return "home/homeList";
    }

    @GetMapping("/home/{id}")
    public String homePage(@PathVariable Long id, Model model) {
        model.addAttribute("homeId", id);
        return "home/home";
    }

    @GetMapping("/home/new")
    public String homeList() {

        return "homes/homeNew";
    }
//
//    @GetMapping("/test")
//    public String test() {
//        return "main/mainPage";
//    }
}
