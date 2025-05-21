package fiap.com.sensorium.domain.filial;

import fiap.com.sensorium.infra.exception.CustomJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FilialRepository extends CustomJpaRepository<Filial, Long> {

    Page<Filial> findByNomeFilialContainingIgnoreCase(String nomeFilial, Pageable pageable);

    @Query("SELECT f FROM Filial f JOIN f.endereco e WHERE LOWER(e.estado) = LOWER(:estado)")
    Page<Filial> findByEstadoIgnoreCase(@Param("estado") String estado, Pageable pageable);

    @Query("SELECT f FROM Filial f JOIN f.endereco e WHERE " +
            "LOWER(f.nomeFilial) LIKE LOWER(concat('%', :nomeFilial, '%')) AND " +
            "LOWER(e.estado) = LOWER(:estado)")
    Page<Filial> findByNomeFilialAndEstado(
            @Param("nomeFilial") String nomeFilial,
            @Param("estado") String estado,
            Pageable pageable);
}
