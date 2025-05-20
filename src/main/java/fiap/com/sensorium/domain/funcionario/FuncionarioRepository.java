package fiap.com.sensorium.domain.funcionario;

import fiap.com.sensorium.domain.dados.Dados;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Page<Funcionario> findByCargoContainingIgnoreCaseAndFiliaisId(String cargo, Long filialId, Pageable pageable);

    Page<Funcionario> findByCargoContainingIgnoreCase(String cargo, Pageable pageable);

    Page<Funcionario> findByFiliaisId(Long filialId, Pageable pageable);
}
