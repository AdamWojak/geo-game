package pl.wojak.geoquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wojak.geoquiz.dto.CountryCreateDTO;
import pl.wojak.geoquiz.entity.UserEntity;
import pl.wojak.geoquiz.repository.CountryRepository;
import pl.wojak.geoquiz.service.GameService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private GameService gameService;

    private final String ANONYMOUS_NAME = "Anonymous";

    @GetMapping("/newgame")
    public String newGame(Model model, HttpSession ses) {
        UserEntity user = (UserEntity) ses.getAttribute("user");
        model.addAttribute("user", user);

        CountryCreateDTO countryForm = gameService.newGame(model, user);
        if (countryForm.getCountries().isEmpty()) {
            return "game/win";
        } else {
            return "game/form01";
        }
    }


}
