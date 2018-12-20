package pl.wojak.geoquiz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wojak.geoquiz.entity.CountryEntity;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<CountryEntity, Long> {

//    @Query(value = "select * from CountryEntity where id not in(select country_id from GuessedEntity)")
//    @Query(value = "select * from CountryEntity order by random() limit 3")
    @Query(value = "select * from geo_schema.country where id not in(select country_id from geo_schema.guessed) order by random() limit 3", nativeQuery = true)
    List<CountryEntity> findRandom3Countries();
//
//    @Query(value = "select c from Country c where c.continent =:continent order by countryName asc")
//    List<CountryEntity> findAllByContinentSortedAsc(@Param("continent") String continent);
//

//    @Query(value = "select * from CountryEntity where id not in(select country_id from GuessedEntity where GuessedEntity.game_id =:game_id) order by rand() limit 3", nativeQuery = true)
//    @Query(value = "select * from CountryEntity c order by rand() limit 3", nativeQuery = true)
//    List<CountryEntity> findRandom3CountriesForOneGame(@Param("game_id") Long id);

    CountryEntity findCountryById(Long id);

    List<CountryEntity> findCountriesByContinent(String continent);

     CountryEntity findCountryByCapital(String capital);
}
