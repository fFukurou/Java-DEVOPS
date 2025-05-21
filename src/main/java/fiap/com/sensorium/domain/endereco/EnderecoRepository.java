package fiap.com.sensorium.domain.endereco;

import fiap.com.sensorium.domain.dados.Dados;
import fiap.com.sensorium.infra.exception.CustomJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends CustomJpaRepository<Endereco, Long> {
}
