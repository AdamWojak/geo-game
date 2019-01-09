package pl.wojak.geoquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.wojak.geoquiz.service.HomeService;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes({"user", "countryForm", "game", "guessed"})
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

    @RequestMapping("game/ended")
    public String ended(Model model, SessionStatus status){
        status.setComplete();
        return "game/ended";
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