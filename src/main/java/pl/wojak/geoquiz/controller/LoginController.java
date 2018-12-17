package pl.wojak.geoquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.wojak.geoquiz.entity.UserEntity;
import pl.wojak.geoquiz.repository.UserRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@SessionAttributes({"user", "game"})
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "user/login";
    }

    @PostMapping("/login")
    public String validateLoginForm(@ModelAttribute("user") @Valid UserEntity user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/login";
        } else {
            UserEntity dbUser = userRepository.findUserByUserName(user.getUserName());
            if (dbUser == null) {
                model.addAttribute("zle", "Nie ma takiego użytkownika!");
                return "user/login";
            }
            if ((dbUser.getUserName().equals(user.getUserName())) && (dbUser.getPassword().equals(user.getPassword()))) {
                model.addAttribute("user", dbUser);
                return "redirect:/";

            } else {
                model.addAttribute("zle", "Nieprawidłowy login bądź hasło!");
                return "user/login";
            }
        }
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        System.out.println("test");
        return "user/logout";
    }

    @GetMapping("/loggedout")
    public String loggedout(Model model, WebRequest request, SessionStatus status) {
        status.setComplete();
        request.removeAttribute("user", WebRequest.SCOPE_SESSION);
        return "user/loggedout";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserEntity());
        return "user/register";
    }

    @PostMapping("/register")
    public String verifyRegister(@ModelAttribute("user") @Valid UserEntity user, BindingResult result, Model model, RedirectAttributes redir) {

        if (result.hasErrors()) {
            return "user/register";
        } else {
            userRepository.save(user);
            redir.addFlashAttribute("registered", "Rejestracja przebiegła poprawnie.");
            return "redirect:/user/login";

        }
    }

}