package pl.wojak.geoquiz.repository;

import org.springframework.data.repository.CrudRepository;
import pl.wojak.geoquiz.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findUserByUserName(String userName);
}
