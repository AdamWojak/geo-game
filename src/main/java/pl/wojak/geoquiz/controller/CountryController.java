package pl.wojak.geoquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wojak.geoquiz.entity.Country;
import pl.wojak.geoquiz.repository.CountryRepository;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountryRepository countryRepository;


    @GetMapping("/{id}")
    public Country pobierzKrajPoId(@PathVariable Long id) {
        Country country = countryRepository.findById(id).orElseThrow(null);
        return country;
    }

    @GetMapping("/capital/{capital}")
    public String pobierzNazweKrajuPoStolicy(@PathVariable String capital) {
        Country country = countryRepository.findCountryByCapital(capital);
        return country.toString();
    }


}
