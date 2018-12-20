package pl.wojak.geoquiz.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.wojak.geoquiz.dto.CountryDTO;
import pl.wojak.geoquiz.dto.CountryFormDTO;
import pl.wojak.geoquiz.entity.CountryEntity;
import pl.wojak.geoquiz.entity.GameEntity;
import pl.wojak.geoquiz.entity.UserEntity;
import pl.wojak.geoquiz.repository.CountryRepository;
import pl.wojak.geoquiz.repository.GameRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public CountryFormDTO newGame(Model model, UserEntity user) {
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

        List<CountryDTO> countriesDTO = findRandom3Countries();
        CountryFormDTO countryForm = new CountryFormDTO(countriesDTO);

        model.addAttribute("countryForm", countryForm);
        model.addAttribute("game", game);

        return countryForm;
    }

    private List<CountryDTO> findRandom3Countries() {

        List<CountryDTO> countriesDTO = new ArrayList<>();
        List<CountryEntity> countries = countryRepository.findRandom3Countries();

        countriesDTO.addAll(countries.stream().map(this::apply).collect(Collectors.toList()));


        return countriesDTO;
    }

    private CountryDTO apply(CountryEntity c) {
        return new CountryDTO(c.getId(), c.getContinent(), c.getCountryName(), c.getCapital(), null, null);
    }

}


//TO SAMO:
//        countriesDTO.addAll(formCountriesDTO.stream().map(this::apply).collect(Collectors.toList()));
//
//                for (CountryEntity country : formCountriesDTO) {
//                countriesDTO.add(apply(country));
//                }