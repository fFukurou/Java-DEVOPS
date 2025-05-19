package fiap.com.sensorium.domain.moto;

import fiap.com.sensorium.domain.dados.Dados;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoRepository extends JpaRepository<Moto, Long> {
}
