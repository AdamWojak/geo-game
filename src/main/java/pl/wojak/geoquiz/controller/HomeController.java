package pl.wojak.geoquiz.controller;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.wojak.geoquiz.constant;
import pl.wojak.geoquiz.entity.UserEntity;
import pl.wojak.geoquiz.repository.UserRepository;

import javax.servlet.http.HttpSession;

import static pl.wojak.geoquiz.constant.ANONYMOUS_NAME;

@Controller
@SessionAttributes({"user", "game"})
public class HomeController {



    @Autowired
    UserRepository userRepository;

    @RequestMapping({"/", "/geoquiz"})
    public String hello(Model model, HttpSession ses) {

        UserEntity user = (UserEntity) ses.getAttribute("user");
        if (user == null) {
            user = new UserEntity();
            user.setUserName(ANONYMOUS_NAME);
        }
//        UserEntity user = userRepository.findById(1L).orElseThrow(null);
        model.addAttribute("user", user);

        return "index";
    }

    @RequestMapping("/game/end")
    public String end(Model model) {

        System.out.println("test");
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