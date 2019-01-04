package pl.wojak.geoquiz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojak.geoquiz.entity.GameEntity;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, Long> {


    List<GameEntity> findAllByUserId(Long id);

    @Query("select g from GameEntity g left join g.user u where u.id =?1 order by g.modificationDate desc")
    List<GameEntity> findAllGamesByUserId(Long id);

}
