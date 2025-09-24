package br.com.fiap.otmav.domain.filial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FilialRepository extends JpaRepository<Filial, Long> {
    @Query("""
            SELECT f FROM Filial f
            WHERE (:nome IS NULL OR LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
              AND (:enderecoId IS NULL OR f.endereco.id = :enderecoId)
            """)
    Page<Filial> findAllFiltered(
            @Param("nome") String nome,
            @Param("enderecoId") Long enderecoId,
            Pageable pageable
    );
}