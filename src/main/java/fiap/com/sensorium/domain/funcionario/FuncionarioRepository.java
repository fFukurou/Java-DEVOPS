package fiap.com.sensorium.domain.funcionario;

import fiap.com.sensorium.domain.dados.Dados;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
