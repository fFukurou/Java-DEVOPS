package fiap.com.sensorium.service;

import fiap.com.sensorium.domain.funcionario.Funcionario;
import fiap.com.sensorium.domain.funcionario.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Page<Funcionario> filterQuery(
            String cargo,
            Long filialId,
            Pageable pageable
    ) {
        if (cargo != null && filialId != null) {
            return funcionarioRepository.findByCargoContainingIgnoreCaseAndFiliaisId(
                    cargo, filialId, pageable);
        } else if (cargo != null) {
            return funcionarioRepository.findByCargoContainingIgnoreCase(cargo, pageable);
        } else if (filialId != null) {
            return funcionarioRepository.findByFiliaisId(filialId, pageable);
        }
        return funcionarioRepository.findAll(pageable);
    }
}
