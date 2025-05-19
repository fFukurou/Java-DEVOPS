package fiap.com.sensorium.domain.patio;

import fiap.com.sensorium.domain.dados.Dados;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatioRepository extends JpaRepository<Patio, Long> {
}
