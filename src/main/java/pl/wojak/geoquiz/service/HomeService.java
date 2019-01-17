package pl.wojak.geoquiz.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.wojak.geoquiz.entity.UserEntity;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Service
public class HomeService {

    public void hello(Model model, HttpSession ses) {

        UserEntity user = (UserEntity) ses.getAttribute("user");
        if (user == null) {
            user = new UserEntity();
        }
        model.addAttribute("user", user);
    }

    public void clearSessionExceptUser(HttpSession ses) {

        if (ses != null) {
            Enumeration sesEnumeration = ses.getAttributeNames();
            while (sesEnumeration.hasMoreElements()) {
                Object atrr = sesEnumeration.nextElement();
                if (atrr.toString().equals("user")) {
                    continue;
                } else {
                    ses.removeAttribute(atrr.toString());
                }
            }
        }
    }
}
