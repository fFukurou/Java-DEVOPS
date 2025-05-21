package fiap.com.sensorium.domain.patio;

import fiap.com.sensorium.domain.dados.Dados;
import fiap.com.sensorium.infra.exception.CustomJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatioRepository extends CustomJpaRepository<Patio, Long> {
}
