package pl.wojak.geoquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wojak.geoquiz.dto.CountryCreateDTO;
import pl.wojak.geoquiz.entity.CountryEntity;
import pl.wojak.geoquiz.entity.GameEntity;
import pl.wojak.geoquiz.entity.UserEntity;
import pl.wojak.geoquiz.repository.CountryRepository;
import pl.wojak.geoquiz.repository.GameRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private GameRepository gameRepository;

    private final String ANONYMOUS_NAME = "Anonymous";

    @GetMapping("/newgame")
    public String newGame(Model model, HttpSession ses) {
        UserEntity user = (UserEntity) ses.getAttribute("user");
        GameEntity game = new GameEntity();

        if (user == null) {
            UserEntity anonymous = new UserEntity(ANONYMOUS_NAME);
            model.addAttribute("user", anonymous);

            // this part of code solve problem with creation of new game through saved games panel
        } else {
            if (user.getUserName().equals(ANONYMOUS_NAME)) {
                game = new GameEntity(user);
                model.addAttribute("game", game);
            } else {
                game = new GameEntity(user);
                gameRepository.save(game);
            }
        }

        model.addAttribute("game", game);

        List<CountryEntity> countries = countryRepository.findRandom3Countries();

        CountryCreateDTO countryForm = new CountryCreateDTO();

        List<String> capitals = new ArrayList<>();

        CountryEntity c1 = countryRepository.findCountryById(1L);
        CountryEntity c2 = countryRepository.findCountryById(2L);
        CountryEntity c3 = countryRepository.findCountryById(3L);
        countryForm.addCountry(c1);
        countryForm.addCountry(c2);
        countryForm.addCountry(c3);


        model.addAttribute("countryForm", countryForm);

        if (countryForm.getCountries().isEmpty()) {
            return "game/win";
        } else {
            return "game/form01";
        }
    }

}
