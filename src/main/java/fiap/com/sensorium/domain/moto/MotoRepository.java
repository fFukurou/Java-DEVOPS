package fiap.com.sensorium.domain.moto;

import fiap.com.sensorium.infra.exception.CustomJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoRepository extends CustomJpaRepository<Moto, Long> {
    Page<Moto> findByCondicaoAndSetorId(String condicao, Long setorId, Pageable pageable);
    Page<Moto> findByCondicaoIgnoreCase(String condicao, Pageable pageable);
    Page<Moto> findBySetorId(Long setorId, Pageable pageable);
}
