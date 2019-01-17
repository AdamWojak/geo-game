package pl.wojak.geoquiz.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.wojak.geoquiz.dto.CountryDTO;
import pl.wojak.geoquiz.dto.CountryFormDTO;
import pl.wojak.geoquiz.dto.GameParamFormDTO;
import pl.wojak.geoquiz.entity.CountryEntity;
import pl.wojak.geoquiz.entity.GameEntity;
import pl.wojak.geoquiz.entity.GuessedEntity;
import pl.wojak.geoquiz.entity.UserEntity;
import pl.wojak.geoquiz.enums.AreaEnum;
import pl.wojak.geoquiz.repository.CountryRepository;
import pl.wojak.geoquiz.repository.GameRepository;
import pl.wojak.geoquiz.repository.GuessedRepository;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.wojak.geoquiz.constant.ANONYMOUS_NAME;

@Service
public class GameService implements CrudService<GameEntity> {


    private final GameRepository gameRepository;
    private final CountryRepository countryRepository;
    private final GuessedRepository guessedRepository;
    private final EmailService emailService;


    public GameService(GameRepository gameRepository, CountryRepository countryRepository,
                       GuessedRepository guessedRepository, EmailService emailService) {
        this.gameRepository = gameRepository;
        this.countryRepository = countryRepository;
        this.guessedRepository = guessedRepository;
        this.emailService = emailService;
    }

    @Override
    public CrudRepository<GameEntity, Long> getRepository() {
        return null;
    }


    public void newGame(Model model, HttpSession ses) {
        UserEntity user = (UserEntity) ses.getAttribute("user");
        model.addAttribute("user", user);
        if (user == null || user.getUserName() == null) {
            user = new UserEntity(ANONYMOUS_NAME);
            model.addAttribute("user", user);
        }
        GameEntity game;
        if (user.getUserName().equals(ANONYMOUS_NAME)) {
            game = new GameEntity();
        } else {
            game = new GameEntity(user);
            setGameNumberForSpecificPlayer(user.getId(), game);
        }
        GameParamFormDTO gameParamFormDTO = new GameParamFormDTO();
        model.addAttribute("gameParamFormDTO", gameParamFormDTO);
        model.addAttribute("continents", gameParamFormDTO.getContinents());
        model.addAttribute("game", game);

    }


    private void setGameNumberForSpecificPlayer(Long userId, GameEntity game) {
        Long userGameId = gameRepository.findLastGameForSpecificPlayer(userId);
        if (userGameId == null) {
            userGameId = 0L;
        }
        game.setUserGameId(userGameId + 1L);
    }

    public void newGameWithParams(GameParamFormDTO gameParamFormDTO, Model model, HttpSession ses) {
        UserEntity user = (UserEntity) ses.getAttribute("user");
        GameEntity game = (GameEntity) ses.getAttribute("game");

        game.setArea(gameParamFormDTO.getContinents().get(0));
        game.setLevel(gameParamFormDTO.getLevels().get(0));

        if (!(user.getUserName().equals(ANONYMOUS_NAME))) {
            game.setModificationDate(LocalDateTime.now());
            gameRepository.save(game);
            emailService.prepareEmail(user.getEmail(), "Stworzyłeś nową grę");
        } else {
            List<Long> idGuessedCountries = new ArrayList<>();
            model.addAttribute("idGuessedCountries", idGuessedCountries);
        }
        findRandom3CountriesAndAddToModel(game, user, model, ses);
    }

    public List<CountryDTO> game(UserEntity user, GameEntity game, Model model, HttpSession ses) {

        if (!(user.getUserName().equals(ANONYMOUS_NAME))) {
            game = gameRepository.findById(game.getId()).orElseThrow(NullPointerException::new);
        }
        List<CountryDTO> countriesDTO = findRandom3CountriesAndAddToModel(game, user, model, ses);
        return countriesDTO;
    }

    private List<CountryDTO> findRandom3CountriesAndAddToModel(GameEntity game, UserEntity user, Model model, HttpSession ses) {

        List<CountryDTO> countriesDTO = new ArrayList<>();
        List<CountryEntity> countries;

        if (user.getUserName().equals(ANONYMOUS_NAME)) {
            countries = findCountriesForAnnonymousUser(game, model, ses);
        } else {
            if (game.getArea().equals(AreaEnum.CALY_SWIAT)) {
                countries = countryRepository.findRandom3CountriesForWholeWorld(game.getId(), game.getLevel().getKod());
            } else {
                countries = countryRepository.findRandom3CountriesForOneContinent(game.getId(), game.getArea().getName(), game.getLevel().getKod());
            }
        }
        countriesDTO.addAll(countries.stream().map(this::apply).collect(Collectors.toList()));
        model.addAttribute("game", game);
        model.addAttribute("countryForm", new CountryFormDTO(countriesDTO));
        return countriesDTO;
    }

    private List<CountryEntity> findCountriesForAnnonymousUser(GameEntity game, Model model, HttpSession ses) {
        List<CountryEntity> countries;
        List<GuessedEntity> guessed = (List<GuessedEntity>) ses.getAttribute("guessed");
        List<Long> idGuessedCountries = (List<Long>) ses.getAttribute("idGuessedCountries");
        if (guessed == null) {
            if (game.getArea().equals(AreaEnum.CALY_SWIAT)) {
                countries = countryRepository.findFirstRandom3CountriesForWholeWorldForAnonymous(game.getLevel().getKod());
            } else {
                countries = countryRepository.findFirstRandom3CountriesForOneContinentForAnonymous(game.getArea().getName(), game.getLevel().getKod());
            }
        } else {
            idGuessedCountries.addAll(guessed.stream().map(g -> g.getCountry().getId()).collect(Collectors.toList()));
            model.addAttribute("idGuessedCountries", idGuessedCountries);

            if (game.getArea().equals(AreaEnum.CALY_SWIAT)) {
                countries = countryRepository.findRandom3CountriesForWholeWorldForAnonymous(game.getLevel().getKod(), idGuessedCountries);
            } else {
                countries = countryRepository.findRandom3CountriesForOneContinentForAnonymous(game.getArea().getName(), game.getLevel().getKod(), idGuessedCountries);
            }
        }
        return countries;
    }


    private CountryDTO apply(CountryEntity c) {
        return new CountryDTO(c.getId(), c.getContinent(), c.getCountryName(), c.getCapital(), null, null);
    }


    public void checkForm(CountryFormDTO countryForm, Model model, HttpSession ses) {
        UserEntity user = (UserEntity) ses.getAttribute("user");
        GameEntity game = (GameEntity) ses.getAttribute("game");

        if (!(user.getUserName().equals(ANONYMOUS_NAME))) {
            game = gameRepository.findById(game.getId()).orElse(null);
        }
        List<CountryDTO> countriesDTO = countryForm.getCountriesFormDTO();
        List<GuessedEntity> guessed = new ArrayList<>();

        setAmountOfPointsAndAttempts(model, game, countriesDTO, guessed);

        if (user.getUserName().equals(ANONYMOUS_NAME)) {
            model.addAttribute("guessed", guessed);
        } else {
            guessedRepository.saveAll(guessed);
            game.setModificationDate(LocalDateTime.now());
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

    public List<GameEntity> createListOfSavedGames(UserEntity user, GameEntity game, Model model) {
        List<GameEntity> games = new ArrayList<>();

        if (user.getUserName().equals(ANONYMOUS_NAME)) {
            game.setId(1L);
            games.add(game);
        } else {
            games = gameRepository.findAllGamesByUserId(user.getId());
        }
        model.addAttribute("games", games);
        return games;
    }


    public void loadSavedGame(Long id, Model model, HttpSession ses) {
        UserEntity user = (UserEntity) ses.getAttribute("user");
        GameEntity game;
        if (user.getUserName().equals(ANONYMOUS_NAME)) {
            game = (GameEntity) ses.getAttribute("game");
        } else {
            game = gameRepository.findById(id).orElse(null);
        }
        model.addAttribute("game", game);
    }

    public void deleteSavedGame(Long id) {
        gameRepository.deleteById(id);
    }

}