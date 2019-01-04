package pl.wojak.geoquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.wojak.geoquiz.dto.CountryDTO;
import pl.wojak.geoquiz.dto.CountryFormDTO;
import pl.wojak.geoquiz.entity.GameEntity;
import pl.wojak.geoquiz.entity.UserEntity;
import pl.wojak.geoquiz.service.GameService;

import javax.servlet.http.HttpSession;
import java.util.List;

import static pl.wojak.geoquiz.constant.ANONYMOUS_NAME;

@Controller
@RequestMapping("/game")
@SessionAttributes({"user", "countryForm", "game", "guessed"})
public class GameController {

    @Autowired
    private GameService gameService;


    @GetMapping("/newgame")
    public String newGame(Model model, HttpSession ses) {

        CountryFormDTO countryForm = gameService.newGame(model, ses);
        if (countryForm.getCountriesFormDTO().isEmpty()) {
            return "game/win";
        } else {
            return "game/form01";
        }
    }

    @GetMapping("/form")
    public String game(Model model, HttpSession ses) {

        List<CountryDTO> countries = gameService.game(model, ses);
        if (countries.isEmpty()) {
            return "game/win";
        } else {
            return "game/form01";
        }
    }

    @PostMapping("/form")
    public String countriesCorrect(@ModelAttribute("countryForm") CountryFormDTO countryForm, Model model, HttpSession ses) {

        gameService.checkForm(countryForm, model, ses);

        return "game/form02";
    }

    @RequestMapping("/saved")
    public String showAllGamesByUserName(Model model, HttpSession ses) {

        UserEntity user = (UserEntity) ses.getAttribute("user");
        GameEntity game = (GameEntity) ses.getAttribute("game");

        if (user.getUserName() == null || user.getUserName() == ANONYMOUS_NAME && game == null) {
            return "game/noSavedGames";
        } else {
            gameService.createListOfSavedGames(user, game, model);
            if (game == null) {
                return "game/noSavedGames";
            }
        }
        return "game/saved";
    }

    @GetMapping("/load")
    public String loadSavedGame(@RequestParam(name = "id") Long id, Model model, HttpSession ses) {

        gameService.loadSavedGame(id, model, ses);
        return "redirect:/game/form";
    }

    @GetMapping("/delete")
    public String deleteSavedGame(@RequestParam(name = "id") Long id) {
        System.out.println(id);
        gameService.deleteSavedGame(id);
        return "redirect:/game/saved";
    }

}
