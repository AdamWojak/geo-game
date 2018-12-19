package pl.wojak.geoquiz.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.wojak.geoquiz.dto.CountryCreateDTO;
import pl.wojak.geoquiz.entity.CountryEntity;
import pl.wojak.geoquiz.entity.GameEntity;
import pl.wojak.geoquiz.entity.UserEntity;
import pl.wojak.geoquiz.repository.CountryRepository;
import pl.wojak.geoquiz.repository.GameRepository;

import javax.servlet.http.HttpSession;
import java.util.List;

import static pl.wojak.geoquiz.constant.ANONYMOUS_NAME;

@Service
public class GameService implements CrudService<GameEntity> {


    private final GameRepository gameRepository;
    private final CountryRepository countryRepository;


    public GameService(GameRepository gameRepository, CountryRepository countryRepository) {
        this.gameRepository = gameRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public CrudRepository<GameEntity, Long> getRepository() {
        return null;
    }

    public CountryCreateDTO newGame(Model model, UserEntity user) {
        GameEntity game = new GameEntity();
        if (user == null) {
             user = new UserEntity(ANONYMOUS_NAME);
            model.addAttribute("user", user);
        } else {
            game = new GameEntity(user);
            if (!user.getUserName().equals(ANONYMOUS_NAME)) {
                gameRepository.save(game);
            }
        }
        CountryCreateDTO random3CountriesDTO = findRandom3Countries();

        model.addAttribute("countryForm", random3CountriesDTO);
        model.addAttribute("game", game);

        return random3CountriesDTO;
    }

    private CountryCreateDTO findRandom3Countries() {
        CountryCreateDTO countryForm = new CountryCreateDTO();
        List<CountryEntity> countries = countryRepository.findRandom3Countries();
        for (CountryEntity country : countries) {
            countryForm.addCountry(country);
        }
        return countryForm;
    }


}
