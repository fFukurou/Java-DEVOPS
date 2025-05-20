package fiap.com.sensorium.domain.situacao;

import fiap.com.sensorium.domain.dados.Dados;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SituacaoRepository extends JpaRepository<Situacao, Long> {
    Page<Situacao> findByStatus(String status, Pageable pageable);
}
