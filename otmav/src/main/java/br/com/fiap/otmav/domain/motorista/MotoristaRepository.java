package br.com.fiap.otmav.domain.motorista;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

    @Query("""
        SELECT m FROM Motorista m
        WHERE (:plano IS NULL OR m.plano = :plano)
          AND (:dadosId IS NULL OR m.dados.id = :dadosId)
        """)
    Page<Motorista> findAllFiltered(
            @Param("plano") MotoristaPlano plano,
            @Param("dadosId") Long dadosId,
            Pageable pageable
    );
}
