package br.com.fiap.otmav.domain.endereco;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("""
        SELECT e FROM Endereco e
        WHERE (:numero IS NULL OR e.numero = :numero)
          AND (:estado IS NULL OR LOWER(e.estado) LIKE LOWER(CONCAT('%', :estado, '%')))
          AND (:codigoPais IS NULL OR LOWER(e.codigoPais) LIKE LOWER(CONCAT('%', :codigoPais, '%')))
          AND (:codigoPostal IS NULL OR LOWER(e.codigoPostal) LIKE LOWER(CONCAT('%', :codigoPostal, '%')))
          AND (:rua IS NULL OR LOWER(e.rua) LIKE LOWER(CONCAT('%', :rua, '%')))
        """)
    Page<Endereco> findAllFiltered(
            @Param("numero") Integer numero,
            @Param("estado") String estado,
            @Param("codigoPais") String codigoPais,
            @Param("codigoPostal") String codigoPostal,
            @Param("rua") String rua,
            Pageable pageable
    );
}
