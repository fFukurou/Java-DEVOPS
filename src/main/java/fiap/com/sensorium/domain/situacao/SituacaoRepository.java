package fiap.com.sensorium.domain.situacao;

import fiap.com.sensorium.domain.dados.Dados;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SituacaoRepository extends JpaRepository<Situacao, Long> {
}
