package pl.wojak.geoquiz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojak.geoquiz.entity.CountryEntity;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<CountryEntity, Long> {

    CountryEntity findCountryByCapital(String capital);

    @Query(value = "select * from geo_schema.country where id not in(select country_id from geo_schema.guessed) order by random() limit 3", nativeQuery = true)
    List<CountryEntity> findRandom3Countries();

    @Query(value = "select * from geo_schema.country where id not in(select country_id from geo_schema.guessed g where g.game_id=?1) order by random() limit 3", nativeQuery = true)
    List<CountryEntity> findRandom3CountriesForOneGame(Long id);


    //   Queries for Anonymous:
    @Query(value = "select * from geo_schema.country c where c.continent=?1 and c.level<=?2 order by random() limit 3", nativeQuery = true)
    List<CountryEntity> findFirstRandom3CountriesForOneContinentForAnonymous(String continent, Integer level);

    @Query(value = "select * from geo_schema.country c where c.level<=?1 order by random() limit 3", nativeQuery = true)
    List<CountryEntity> findFirstRandom3CountriesForWholeWorldForAnonymous(Integer level);

    @Query(value = "select * from geo_schema.country c where c.continent=?1 and c.level<=?2 and c.id not in (?3) order by random() limit 3", nativeQuery = true)
    List<CountryEntity> findRandom3CountriesForOneContinentForAnonymous(String continent, Integer level, List<Long> idGuessed);

    @Query(value = "select * from geo_schema.country where level<=?1 and id not in(?2) order by random() limit 3", nativeQuery = true)
    List<CountryEntity> findRandom3CountriesForWholeWorldForAnonymous(Integer id, List<Long> idGuessed);


    //    Queries for user:
    @Query(value = "select * from geo_schema.country where continent=?2 and level<=?3 and id not in(select country_id from geo_schema.guessed g where g.game_id=?1) order by random() limit 3", nativeQuery = true)
    List<CountryEntity> findRandom3CountriesForOneContinent(Long id, String continent, Integer level);

    @Query(value = "select * from geo_schema.country where level<=?2 and id not in(select country_id from geo_schema.guessed g where g.game_id=?1) order by random() limit 3", nativeQuery = true)
    List<CountryEntity> findRandom3CountriesForWholeWorld(Long id, Integer level);


}
