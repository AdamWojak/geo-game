package pl.wojak.geoquiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private final String USERNAME = "Anonymous";

    @RequestMapping("/")
    public String hello(Model model) {

        model.addAttribute("userName", USERNAME);

        return "index";
    }

    @RequestMapping("/game/end")
    public String end() {
        return "game/end";
    }

    @RequestMapping("/game/win")
    public String win() {
        return "game/win";
    }

    @RequestMapping("/commingSoon")
    public String commingSoon() {
        return "commingSoon";
    }

}