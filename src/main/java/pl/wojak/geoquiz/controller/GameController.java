package pl.wojak.geoquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.wojak.geoquiz.dto.CountryFormDTO;
import pl.wojak.geoquiz.entity.GameEntity;
import pl.wojak.geoquiz.entity.GuessedEntity;
import pl.wojak.geoquiz.entity.UserEntity;
import pl.wojak.geoquiz.repository.CountryRepository;
import pl.wojak.geoquiz.service.GameService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static pl.wojak.geoquiz.constant.ANONYMOUS_NAME;

@Controller
@RequestMapping("/game")
@SessionAttributes({"user", "countryForm", "game"})
public class GameController {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private GameService gameService;


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

    @PostMapping("/form")
    public String countriesCorrect(@ModelAttribute("countryForm") CountryFormDTO countryForm, Model model, HttpSession ses) {

        UserEntity user = (UserEntity) ses.getAttribute("user");
        GameEntity game = (GameEntity) ses.getAttribute("game");
//        CountryFormDTO countryForm = (CountryFormDTO) ses.getAttribute("countryForm");

        if (!(user.getUserName().equals(ANONYMOUS_NAME))) {
            game = gameService.findById(game.getId()).orElse(null);
        }

        List<GuessedEntity> guessed = new ArrayList<>();

        int amountOfPoints = 0;
        int amountOfAttempts = 0;

//        for (int i = 0; i < countryForm.getFormCountriesDTO().size(); i++) {
//            String cap = countryForm.getFormCountriesDTO().get(i).getCapital();
//            if (cap.equals(countryForm.getgetGuessedCapital().get(i))) {
//                result.add("TAK");
//                amountOfPoints++;
//                amountOfAttempts++;
//                Guessed guess = new Guessed(game, formCountriesDTO.get(i));
//                guessed.add(guess);
//            } else {
//                result.add("NIE");
//                amountOfAttempts++;
//            }
//
//        }

        model.addAttribute("countryForm", countryForm);
        model.addAttribute("amountOfPoints", amountOfPoints);
        model.addAttribute("amountOfAttempts", amountOfAttempts);


        return "game/form02";
    }

}
