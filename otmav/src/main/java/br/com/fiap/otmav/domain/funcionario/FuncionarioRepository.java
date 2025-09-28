package br.com.fiap.otmav.domain.funcionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {


    @Query("SELECT f FROM Funcionario f WHERE LOWER(f.dados.email) = LOWER(:email)")
    Optional<Funcionario> findByDadosEmail(@Param("email") String email);


    @Query("""
            SELECT f FROM Funcionario f
            WHERE (:cargo IS NULL OR LOWER(f.cargo) LIKE LOWER(CONCAT('%', :cargo, '%')))
              AND (:filialId IS NULL OR f.filial.id = :filialId)
              AND (:dadosId IS NULL OR f.dados.id = :dadosId)
            """)
    Page<Funcionario> findAllFiltered(
            @Param("cargo") String cargo,
            @Param("filialId") Long filialId,
            @Param("dadosId") Long dadosId,
            Pageable pageable
    );

    @Query("SELECT f FROM Funcionario f JOIN FETCH f.dados WHERE f.id = :id")
    Optional<Funcionario> findByIdWithDados(@Param("id") Long id);

    @Query("SELECT f FROM Funcionario f JOIN FETCH f.dados d WHERE d.email = :email")
    Optional<Funcionario> findByDadosEmailFetchDados(@Param("email") String email);

}
