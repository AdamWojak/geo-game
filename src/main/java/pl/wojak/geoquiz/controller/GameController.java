package pl.wojak.geoquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.wojak.geoquiz.dto.CountryDTO;
import pl.wojak.geoquiz.dto.CountryFormDTO;
import pl.wojak.geoquiz.entity.CountryEntity;
import pl.wojak.geoquiz.entity.GameEntity;
import pl.wojak.geoquiz.entity.GuessedEntity;
import pl.wojak.geoquiz.entity.UserEntity;
import pl.wojak.geoquiz.repository.CountryRepository;
import pl.wojak.geoquiz.repository.GameRepository;
import pl.wojak.geoquiz.repository.GuessedRepository;
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

        List<CountryDTO> countriesDTO = countryForm.getFormCountriesDTO();

        for (int i = 0; i < countriesDTO.size(); i++) {
            String cap = countriesDTO.get(i).getCapital();
            if (cap.equals(countriesDTO.get(i).getGuessedCapital())) {
                countriesDTO.get(i).setResult(true);
                amountOfPoints++;
                amountOfAttempts++;
                CountryEntity countryEntity = countryRepository.findById(countriesDTO.get(i).getId()).orElseThrow(NullPointerException::new);
                GuessedEntity guess = new GuessedEntity(game, countryEntity);
                guessed.add(guess);
            } else {
                countriesDTO.get(i).setResult(false);
                amountOfAttempts++;
            }

        }

        game.setAmountOfPoints(game.getAmountOfPoints() + amountOfPoints);
        game.setAmountOfAttempts(game.getAmountOfAttempts() + amountOfAttempts);

        if (user.getUserName().equals(ANONYMOUS_NAME)) {
            model.addAttribute("game", game);
            model.addAttribute("guessed", guessed);
        } else {
            guessedRepository.saveAll(guessed);
            gameRepository.save(game);
        }

        model.addAttribute("countryForm", countryForm);
        model.addAttribute("amountOfPoints", amountOfPoints);
        model.addAttribute("amountOfAttempts", amountOfAttempts);


        return "game/form02";
    }

}
