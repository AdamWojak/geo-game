package pl.wojak.geoquiz.repository;

import org.springframework.data.repository.CrudRepository;
import pl.wojak.geoquiz.entity.CountryEntity;

import java.util.List;

public interface CountryRepository extends CrudRepository<CountryEntity, Long> {

    List<CountryEntity> findCountriesByContinent(String continent);

     CountryEntity findCountryByCapital(String capital);
}
