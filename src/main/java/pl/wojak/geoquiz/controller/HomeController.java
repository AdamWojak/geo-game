package pl.wojak.geoquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
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
    public String end(Model model, WebRequest request, SessionStatus status) {
        return "game/end";
    }

    @RequestMapping("game/ended")
    public String ended(Model model, WebRequest request, SessionStatus status){
        status.setComplete();
        request.removeAttribute("countryForm", WebRequest.SCOPE_SESSION);
        request.removeAttribute("game", WebRequest.SCOPE_SESSION);
        request.removeAttribute("guessed", WebRequest.SCOPE_SESSION);
        return "index";
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