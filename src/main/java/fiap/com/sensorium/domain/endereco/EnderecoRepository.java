package fiap.com.sensorium.domain.endereco;

import fiap.com.sensorium.domain.dados.Dados;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
