package fiap.com.sensorium.domain.situacao;

import fiap.com.sensorium.infra.exception.CustomJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SituacaoRepository extends CustomJpaRepository<Situacao, Long> {
    Page<Situacao> findByStatusIgnoreCase(String status, Pageable pageable);
}
