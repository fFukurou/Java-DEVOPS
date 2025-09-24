package br.com.fiap.otmav.domain.dados;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DadosRepository extends JpaRepository<Dados, Long> {
    Optional<Dados> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    Optional<Dados> findByEmail(String email);

    @Query("""
            SELECT d FROM Dados d
            WHERE (:cpf IS NULL OR d.cpf = :cpf)
              AND (:nome IS NULL OR LOWER(d.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
              AND (:email IS NULL OR LOWER(d.email) LIKE LOWER(CONCAT('%', :email, '%')))
            """)
    Page<Dados> findAllFiltered(
            @Param("cpf") String cpf,
            @Param("nome") String nome,
            @Param("email") String email,
            Pageable pageable
    );
}
