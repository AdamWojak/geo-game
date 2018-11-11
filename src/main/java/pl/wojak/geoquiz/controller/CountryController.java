package pl.wojak.geoquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wojak.geoquiz.entity.CountryEntity;
import pl.wojak.geoquiz.repository.CountryRepository;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountryRepository countryRepository;


    @GetMapping("/{id}")
    public CountryEntity pobierzKrajPoId(@PathVariable Long id) {
        CountryEntity countryEntity = countryRepository.findById(id).orElseThrow(null);
        return countryEntity;
    }

    @GetMapping("/capital/{capital}")
    public String pobierzNazweKrajuPoStolicy(@PathVariable String capital) {
        CountryEntity countryEntity = countryRepository.findCountryByCapital(capital);
        return countryEntity.toString();
    }


}
