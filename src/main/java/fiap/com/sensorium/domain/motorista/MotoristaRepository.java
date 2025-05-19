package fiap.com.sensorium.domain.motorista;

import fiap.com.sensorium.domain.dados.Dados;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {
}
