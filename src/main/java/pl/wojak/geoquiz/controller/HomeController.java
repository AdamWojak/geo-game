package pl.wojak.geoquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.wojak.geoquiz.service.HomeService;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes({"user", "game"})
public class HomeController {

    @Autowired
    HomeService homeService;

    @RequestMapping({"/", "/geoquiz"})
    public String hello(Model model, HttpSession ses) {

        homeService.hello(model, ses);
        return "index";
    }

    @RequestMapping("/game/end")
    public String end(Model model) {
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