package pl.wojak.geoquiz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.wojak.geoquiz.entity.CountryEntity;

import java.util.List;

public interface CountryRepository extends CrudRepository<CountryEntity, Long> {

    @Query(value = "select * from Country where id not in(select country_id from Guessed) order by rand() limit 3", nativeQuery = true)
    List<CountryEntity> findRandom3Countries();

    @Query(value = "select c from Country c where c.continent =:continent order by countryName asc")
    List<CountryEntity> findAllByContinentSortedAsc(@Param("continent") String continent);

    @Query(value = "select * from Country where id not in(select country_id from Guessed where Guessed.game_id =:game_id) order by rand() limit 3", nativeQuery = true)
    List<CountryEntity> findRandom3CountriesForOneGame(@Param("game_id") Long id);



    List<CountryEntity> findCountriesByContinent(String continent);

     CountryEntity findCountryByCapital(String capital);
}
