package br.com.fiap.otmav.domain.patio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatioRepository extends JpaRepository<Patio, Long> {

    @Query("""
        SELECT p FROM Patio p
        WHERE (:totalMotos IS NULL OR p.totalMotos = :totalMotos)
          AND (:capacidadeMoto IS NULL OR p.capacidadeMoto = :capacidadeMoto)
          AND (:filialId IS NULL OR p.filial.id = :filialId)
          AND (:regiaoId IS NULL OR p.regiao.id = :regiaoId)
        """)
    Page<Patio> findAllFiltered(
            @Param("totalMotos") Integer totalMotos,
            @Param("capacidadeMoto") Integer capacidadeMoto,
            @Param("filialId") Long filialId,
            @Param("regiaoId") Long regiaoId,
            Pageable pageable
    );
}
