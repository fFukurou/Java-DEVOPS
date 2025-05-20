package fiap.com.sensorium.service;

import fiap.com.sensorium.domain.funcionario.Funcionario;
import fiap.com.sensorium.domain.funcionario.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Cacheable(value = "funcionarios",
            key = "{#cargo, #filialId, #pageable.pageNumber, #pageable.pageSize, #pageable.sort.toString()}")
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

    @Cacheable(value = "funcionario", key = "#id")
    public Optional<Funcionario> findById(Long id) {
        return funcionarioRepository.findById(id);
    }

    @CacheEvict(value = {"funcionarios", "funcionario"}, allEntries = true)
    public void clearCache() {

    }
}
