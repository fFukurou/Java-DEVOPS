package br.com.fiap.otmav.domain.modelo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    @Query("""
        SELECT m FROM Modelo m
        WHERE (:nome IS NULL OR LOWER(m.nomeModelo) LIKE LOWER(CONCAT('%', :nome, '%')))
          AND (:tipoCombustivel IS NULL OR LOWER(m.tipoCombustivel) LIKE LOWER(CONCAT('%', :tipoCombustivel, '%')))
          AND (:tanque IS NULL OR m.tanque = :tanque)
        """)
    Page<Modelo> findAllFiltered(
            @Param("nome") String nome,
            @Param("tipoCombustivel") String tipoCombustivel,
            @Param("tanque") Integer tanque,
            Pageable pageable
    );
}
