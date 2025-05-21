package fiap.com.sensorium.domain.dados;

import fiap.com.sensorium.infra.exception.CustomJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosRepository extends CustomJpaRepository<Dados, Long> {

}
