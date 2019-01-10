package pl.wojak.geoquiz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojak.geoquiz.entity.CountryEntity;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<CountryEntity, Long> {

    @Query(value = "select * from geo_schema.country where id not in(select country_id from geo_schema.guessed) order by random() limit 3", nativeQuery = true)
    List<CountryEntity> findRandom3Countries();

    @Query(value = "select * from geo_schema.country where id not in(select country_id from geo_schema.guessed g where g.game_id=?1) order by random() limit 3", nativeQuery = true)
    List<CountryEntity> findRandom3CountriesForOneGame(Long id);

    CountryEntity findCountryById(Long id);

    List<CountryEntity> findCountriesByContinent(String continent);

    CountryEntity findCountryByCapital(String capital);

    @Query("select distinct c.continent from CountryEntity c order by c.continent")
    List<String> findListOfContinents();
}
