package br.com.fiap.otmav.domain.regiao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegiaoRepository extends JpaRepository<Regiao, Long> {

    @Query("""
        SELECT r FROM Regiao r
        WHERE (:area IS NULL OR r.area = :area)
          AND (:searchWkt IS NULL OR LOWER(r.localizacaoWkt) LIKE LOWER(CONCAT('%', :searchWkt, '%')))
        """)
    Page<Regiao> findAllFiltered(
            @Param("area") Double area,
            @Param("searchWkt") String searchWkt,
            Pageable pageable
    );
}
