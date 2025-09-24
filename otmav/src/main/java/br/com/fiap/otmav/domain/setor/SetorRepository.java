package br.com.fiap.otmav.domain.setor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SetorRepository extends JpaRepository<Setor, Long> {

    @Query("""
        SELECT s FROM Setor s
        WHERE (:qtdMoto IS NULL OR s.qtdMoto = :qtdMoto)
          AND (:capacidade IS NULL OR s.capacidade = :capacidade)
          AND (:nome IS NULL OR LOWER(s.nomeSetor) LIKE LOWER(CONCAT('%', :nome, '%')))
          AND (:cor IS NULL OR LOWER(s.cor) = LOWER(:cor))
          AND (:patioId IS NULL OR s.patio.id = :patioId)
          AND (:regiaoId IS NULL OR s.regiao.id = :regiaoId)
        """)
    Page<Setor> findAllFiltered(
            @Param("qtdMoto") Integer qtdMoto,
            @Param("capacidade") Integer capacidade,
            @Param("nome") String nome,
            @Param("cor") String cor,
            @Param("patioId") Long patioId,
            @Param("regiaoId") Long regiaoId,
            Pageable pageable
    );
}
