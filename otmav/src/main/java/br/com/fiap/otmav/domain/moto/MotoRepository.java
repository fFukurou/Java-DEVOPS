package br.com.fiap.otmav.domain.moto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MotoRepository extends JpaRepository<Moto, Long> {

    @Query("""
        SELECT m FROM Moto m
        WHERE (:placa IS NULL OR LOWER(m.placa) LIKE LOWER(CONCAT('%', :placa, '%')))
          AND (:chassi IS NULL OR LOWER(m.chassi) LIKE LOWER(CONCAT('%', :chassi, '%')))
          AND (:condicao IS NULL OR LOWER(m.condicao) = LOWER(:condicao))
          AND (:motoristaId IS NULL OR m.motorista.id = :motoristaId)
          AND (:modeloId IS NULL OR m.modelo.id = :modeloId)
          AND (:setorId IS NULL OR m.setor.id = :setorId)
          AND (:situacaoId IS NULL OR m.situacao.id = :situacaoId)
        """)
    Page<Moto> findAllFiltered(
            @Param("placa") String placa,
            @Param("chassi") String chassi,
            @Param("condicao") String condicao,
            @Param("motoristaId") Long motoristaId,
            @Param("modeloId") Long modeloId,
            @Param("setorId") Long setorId,
            @Param("situacaoId") Long situacaoId,
            Pageable pageable
    );

    boolean existsByPlaca(String placa);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Moto m WHERE m.placa = :placa AND m.id <> :id")
    boolean existsByPlacaAndIdNot(@Param("placa") String placa, @Param("id") Long id);
}
