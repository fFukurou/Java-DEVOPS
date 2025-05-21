package fiap.com.sensorium.infra.exception;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

// TODO: make ExceptionHandling more automatic and modular for future sprints...
@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {
    default T findByIdOrThrow(ID id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
    }
}