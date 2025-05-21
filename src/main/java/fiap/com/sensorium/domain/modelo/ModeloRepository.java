package fiap.com.sensorium.domain.modelo;

import fiap.com.sensorium.domain.dados.Dados;
import fiap.com.sensorium.infra.exception.CustomJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeloRepository extends CustomJpaRepository<Modelo, Long> {
    Page<Modelo> findByNomeContainingIgnoreCaseAndCombustivelIgnoreCase(String nomeModelo, String combustivel, Pageable pageable);

    Page<Modelo> findByNomeContainingIgnoreCase(String nomeModelo, Pageable pageable);

    Page<Modelo> findByCombustivelIgnoreCase(String combustivel, Pageable pageable);

    boolean existsById(Long id);
}
