package pl.wojak.geoquiz.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.wojak.geoquiz.entity.UserEntity;

import javax.servlet.http.HttpSession;

@Service
public class HomeService {

    public void hello(Model model, HttpSession ses) {

        UserEntity user = (UserEntity) ses.getAttribute("user");
        if (user == null) {
            user = new UserEntity();
        }
        model.addAttribute("user", user);
    }
}
