package fiap.com.sensorium.domain.setor;

import fiap.com.sensorium.domain.dados.Dados;
import fiap.com.sensorium.infra.exception.CustomJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetorRepository extends CustomJpaRepository<Setor, Long> {
}
