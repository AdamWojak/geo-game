package pl.wojak.geoquiz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wojak.geoquiz.entity.GameEntity;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, Long> {


    @Query(value = "select g from GameEntity g where user.id =:id")
    GameEntity findGameByUserId(@Param("id") Long id);

    @Query(value = "select g from GameEntity g where user.userName =:userName")
    GameEntity findGameByUserName(@Param("userName") String userName);

    List<GameEntity> findAllByUserId(Long id);

}
