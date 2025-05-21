package fiap.com.sensorium.service;

import fiap.com.sensorium.domain.modelo.CreateModeloDto;
import fiap.com.sensorium.domain.modelo.Modelo;
import fiap.com.sensorium.domain.modelo.ModeloRepository;
import fiap.com.sensorium.domain.modelo.UpdateModeloDto;
import fiap.com.sensorium.infra.exception.EntityNotFoundException;
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

    @CacheEvict(value = {"modelos", "modelo"}, allEntries = true)
    public Modelo create(Modelo modelo) {
        return modeloRepository.save(modelo);
    }

    @CachePut(value = "modelo", key = "#modelo.id")
    @CacheEvict(value = "modelos", allEntries = true)
    public Modelo update(Modelo modelo) {
        return modeloRepository.save(modelo);
    }

    @CacheEvict(value = {"modelos", "modelo"}, key = "#id")
    public void delete(Long id) {
        modeloRepository.deleteById(id);
    }

    public Modelo findByIdOrThrow(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Modelo não encontrado"));
    }

    @Cacheable(value = "modelo", key = "#id")
    public Optional<Modelo> findById(Long id) {
        return modeloRepository.findById(id);
    }
    @CacheEvict(value = {"modelos", "modelo"}, allEntries = true)
    public void clearCache() {
    }

    // HELPER METHOD
    public void copyDtoToModel(Object dto, Modelo modelo) {
        if (dto instanceof CreateModeloDto createDto) {
            modelo.setNome(createDto.nome());
            modelo.setFrenagem(createDto.frenagem());
            modelo.setSistemaPartida(createDto.sistemaPartida());
            modelo.setTanque(createDto.tanque());
            modelo.setCombustivel(createDto.combustivel());
            modelo.setConsumo(createDto.consumo());
        } else if (dto instanceof UpdateModeloDto updateDto) {
            modelo.setNome(updateDto.nome());
            modelo.setFrenagem(updateDto.frenagem());
            modelo.setSistemaPartida(updateDto.sistemaPartida());
            modelo.setTanque(updateDto.tanque());
            modelo.setCombustivel(updateDto.combustivel());
            modelo.setConsumo(updateDto.consumo());
        }
    }
}