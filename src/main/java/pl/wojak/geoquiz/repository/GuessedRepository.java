package pl.wojak.geoquiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojak.geoquiz.entity.GuessedEntity;

@Repository
public interface GuessedRepository extends CrudRepository<GuessedEntity, Long> {

}
