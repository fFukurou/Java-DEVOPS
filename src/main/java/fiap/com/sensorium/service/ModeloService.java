package fiap.com.sensorium.service;

import fiap.com.sensorium.domain.modelo.Modelo;
import fiap.com.sensorium.domain.modelo.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    @Cacheable(value = "modelos", key = "{#nomeModelo, #combustivel, #pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    public Page<Modelo> filterQuery(
            String nomeModelo,
            String combustivel,
            Pageable pageable
    ) {
        if (nomeModelo != null && combustivel != null) {
            return modeloRepository.findByNomeContainingIgnoreCaseAndCombustivelIgnoreCase(
                    nomeModelo, combustivel, pageable);
        } else if (nomeModelo != null) {
            return modeloRepository.findByNomeContainingIgnoreCase(nomeModelo, pageable);
        } else if (combustivel != null) {
            return modeloRepository.findByCombustivelIgnoreCase(combustivel, pageable);
        }
        return modeloRepository.findAll(pageable);
    }

    @Cacheable(value = "modelo", key = "#id")
    public Optional<Modelo> findById(Long id) {
        return modeloRepository.findById(id);
    }

    @CacheEvict(value = {"modelos", "modelo"}, allEntries = true)
    public void clearCache() {
    }
}