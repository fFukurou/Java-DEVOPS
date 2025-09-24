package br.com.fiap.otmav.domain.situacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SituacaoRepository extends JpaRepository<Situacao, Long> {

    @Query("""
        SELECT s FROM Situacao s
        WHERE (:nome IS NULL OR LOWER(s.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
          AND (:status IS NULL OR s.status = :status)
        """)
    Page<Situacao> findAllFiltered(
            @Param("nome") String nome,
            @Param("status") SituacaoStatus status,
            Pageable pageable
    );
}
