package pl.wojak.geoquiz.service;

import com.google.common.collect.Lists;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {

    CrudRepository<T, Long> getRepository();

    default T save(T t) {
        return getRepository().save(t);
    }

    default List<T> findAll() {
        return Lists.newArrayList(getRepository().findAll());
    }

    default Optional<T> findById(Long id) {
        return getRepository().findById(id);
    }

}
