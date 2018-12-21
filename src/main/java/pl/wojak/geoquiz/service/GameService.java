package pl.wojak.geoquiz.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.wojak.geoquiz.dto.CountryDTO;
import pl.wojak.geoquiz.dto.CountryFormDTO;
import pl.wojak.geoquiz.entity.CountryEntity;
import pl.wojak.geoquiz.entity.GameEntity;
import pl.wojak.geoquiz.entity.GuessedEntity;
import pl.wojak.geoquiz.entity.UserEntity;
import pl.wojak.geoquiz.repository.CountryRepository;
import pl.wojak.geoquiz.repository.GameRepository;
import pl.wojak.geoquiz.repository.GuessedRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.wojak.geoquiz.constant.ANONYMOUS_NAME;

@Service
public class GameService implements CrudService<GameEntity> {


    private final GameRepository gameRepository;
    private final CountryRepository countryRepository;
    private final GuessedRepository guessedRepository;


    public GameService(GameRepository gameRepository, CountryRepository countryRepository, GuessedRepository guessedRepository) {
        this.gameRepository = gameRepository;
        this.countryRepository = countryRepository;
        this.guessedRepository = guessedRepository;
    }

    @Override
    public CrudRepository<GameEntity, Long> getRepository() {
        return null;
    }


    public CountryFormDTO newGame(Model model, UserEntity user) {
        if (user == null || user.getUserName() == null) {
            user = new UserEntity(ANONYMOUS_NAME);
            model.addAttribute("user", user);
        }
        GameEntity game = new GameEntity(user);
        if (!user.getUserName().equals(ANONYMOUS_NAME)) {
            gameRepository.save(game);
        }

        List<CountryDTO> countriesDTO = findRandom3CountriesforOneGame(game);
        CountryFormDTO countryForm = new CountryFormDTO(countriesDTO);

        model.addAttribute("countryForm", countryForm);
        model.addAttribute("game", game);
        return countryForm;
    }

    public List<CountryDTO> game(UserEntity user, GameEntity game, Model model) {

        if (!(user.getUserName().equals(ANONYMOUS_NAME))) {
            game = gameRepository.findById(game.getId()).orElseThrow(NullPointerException::new);
        }
        List<CountryDTO> countriesDTO = findRandom3CountriesforOneGame(game);
        model.addAttribute("game", game);
        model.addAttribute("countryForm", new CountryFormDTO(countriesDTO));
        return countriesDTO;
    }

    private List<CountryDTO> findRandom3CountriesforOneGame(GameEntity game) {
        List<CountryDTO> countriesDTO = new ArrayList<>();
        List<CountryEntity> countries = countryRepository.findRandom3CountriesForOneGame(game.getId());

        countriesDTO.addAll(countries.stream().map(this::apply).collect(Collectors.toList()));
        return countriesDTO;
    }

    private CountryDTO apply(CountryEntity c) {
        return new CountryDTO(c.getId(), c.getContinent(), c.getCountryName(), c.getCapital(), null, null);
    }


    public void checkForm(UserEntity user, GameEntity game, CountryFormDTO countryForm, Model model) {
        if (!(user.getUserName().equals(ANONYMOUS_NAME))) {
            game = gameRepository.findById(game.getId()).orElse(null);
        }
        List<CountryDTO> countriesDTO = countryForm.getFormCountriesDTO();
        List<GuessedEntity> guessed = new ArrayList<>();

        setAmountOfPointsAndAttempts(model, game, countriesDTO, guessed);

        if (user.getUserName().equals(ANONYMOUS_NAME)) {
            model.addAttribute("guessed", guessed);
        } else {
            guessedRepository.saveAll(guessed);
            gameRepository.save(game);
        }
    }


    private void setAmountOfPointsAndAttempts(Model model, GameEntity game, List<CountryDTO> countriesDTO, List<GuessedEntity> guessed) {
        int amountOfPoints = 0;
        int amountOfAttempts = 0;

        for (CountryDTO country : countriesDTO) {
            if (country.getCapital().equals(country.getGuessedCapital())) {
                country.setResult(true);
                amountOfPoints++;
                amountOfAttempts++;
                CountryEntity countryEntity = countryRepository.findById(country.getId()).orElseThrow(NullPointerException::new);
                guessed.add(new GuessedEntity(game, countryEntity));
            } else {
                country.setResult(false);
                amountOfAttempts++;
            }
        }
        game.setAmountOfPoints(game.getAmountOfPoints() + amountOfPoints);
        game.setAmountOfAttempts(game.getAmountOfAttempts() + amountOfAttempts);

        model.addAttribute("game", game);
        model.addAttribute("amountOfPoints", amountOfPoints);
        model.addAttribute("amountOfAttempts", amountOfAttempts);
    }
}


//TO SAMO:
//        countriesDTO.addAll(formCountriesDTO.stream().map(this::apply).collect(Collectors.toList()));
//
//                for (CountryEntity country : formCountriesDTO) {
//                countriesDTO.add(apply(country));
//                }