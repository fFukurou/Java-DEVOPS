package fiap.com.sensorium.service;

import fiap.com.sensorium.domain.motorista.Motorista;
import fiap.com.sensorium.domain.motorista.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MotoristaService {

    @Autowired
    private MotoristaRepository motoristaRepository;

    @Cacheable(value = "motoristas",
            key = "{#plano, #pageable.pageNumber, #pageable.pageSize, #pageable.sort.toString()}")
    public Page<Motorista> filterQuery(String plano, Pageable pageable) {
        return (plano != null)
                ? motoristaRepository.findByPlanoContainingIgnoreCase(plano, pageable)
                : motoristaRepository.findAll(pageable);
    }

    @Cacheable(value = "motorista", key = "#id")
    public Optional<Motorista> findById(Long id) {
        return motoristaRepository.findById(id);
    }

    @CacheEvict(value = {"motoristas", "motorista"}, allEntries = true)
    public void clearCache() {

    }
}
