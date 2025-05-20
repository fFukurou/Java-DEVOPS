package fiap.com.sensorium.domain.moto;

import fiap.com.sensorium.domain.dados.Dados;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    Page<Moto> findByCondicaoAndSetorId(String condicao, Long setorId, Pageable pageable);
    Page<Moto> findByCondicao(String condicao, Pageable pageable);
    Page<Moto> findBySetorId(Long setorId, Pageable pageable);
}
