package fiap.com.sensorium.service;

import fiap.com.sensorium.domain.filial.Filial;
import fiap.com.sensorium.domain.filial.FilialRepository;
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
public class FilialService {

    @Autowired
    private FilialRepository filialRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Cacheable(value = "filiais",
            key = "{#nomeFilial, #estado, #pageable.pageNumber, #pageable.pageSize, #pageable.sort.toString()}")
    public Page<Filial> filterQuery(
            String nomeFilial,
            String estado,
            Pageable pageable
    ) {
        if (nomeFilial != null && estado != null) {
            return filialRepository.findByNomeFilialAndEstado(nomeFilial, estado, pageable);
        } else if (nomeFilial != null) {
            return filialRepository.findByNomeFilialContainingIgnoreCase(nomeFilial, pageable);
        } else if (estado != null) {
            return filialRepository.findByEstadoIgnoreCase(estado, pageable);
        }
        return filialRepository.findAll(pageable);
    }

    @Cacheable(value = "filial", key = "#id")
    public Optional<Filial> findById(Long id) {
        return filialRepository.findById(id);
    }

    @CacheEvict(value = {"filiais", "filial"}, allEntries = true)
    public void clearCache() {

    }

}