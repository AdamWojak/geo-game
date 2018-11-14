package pl.wojak.geoquiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wojak.geoquiz.entity.UserEntity;

@Controller
public class HomeController {

    private final String USERNAME = "Anonymous";

    @RequestMapping("/")
    public String hello(Model model) {

        UserEntity u1 = new UserEntity();
        u1.setUserName("adam");
//        u1.setUserName("anonymous");
        model.addAttribute("user", u1);

// https://www.thymeleaf.org/doc/articles/springmvcaccessdata.html
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