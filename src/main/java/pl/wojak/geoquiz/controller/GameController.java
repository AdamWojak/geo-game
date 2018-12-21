package pl.wojak.geoquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.wojak.geoquiz.dto.CountryDTO;
import pl.wojak.geoquiz.dto.CountryFormDTO;
import pl.wojak.geoquiz.entity.GameEntity;
import pl.wojak.geoquiz.entity.UserEntity;
import pl.wojak.geoquiz.repository.CountryRepository;
import pl.wojak.geoquiz.repository.GameRepository;
import pl.wojak.geoquiz.repository.GuessedRepository;
import pl.wojak.geoquiz.service.GameService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/game")
@SessionAttributes({"user", "countryForm", "game", "guessed"})
public class GameController {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GuessedRepository guessedRepository;


    @GetMapping("/newgame")
    public String newGame(Model model, HttpSession ses) {
        UserEntity user = (UserEntity) ses.getAttribute("user");
        model.addAttribute("user", user);

        CountryFormDTO countryForm = gameService.newGame(model, user);
        if (countryForm.getFormCountriesDTO().isEmpty()) {
            return "game/win";
        } else {
            return "game/form01";
        }
    }

    @GetMapping("/form")
    public String game(Model model, HttpSession ses) {

        UserEntity user = (UserEntity) ses.getAttribute("user");
        GameEntity game = (GameEntity) ses.getAttribute("game");

        List<CountryDTO> countries = gameService.game(user, game, model);

        if (countries.isEmpty()) {
            return "game/win";
        } else {
            return "game/form01";
        }
    }

    @PostMapping("/form")
    public String countriesCorrect(@ModelAttribute("countryForm") CountryFormDTO countryForm, Model model, HttpSession ses) {

        UserEntity user = (UserEntity) ses.getAttribute("user");
        GameEntity game = (GameEntity) ses.getAttribute("game");

        gameService.checkForm(user, game, countryForm, model);

        return "game/form02";
    }

}
