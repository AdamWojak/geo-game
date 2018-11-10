package pl.wojak.geoquiz.repository;

import org.springframework.data.repository.CrudRepository;
import pl.wojak.geoquiz.entity.Country;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Long> {

    List<Country> findCountriesByContinent(String continent);

     Country findCountryByCapital(String capital);
}
