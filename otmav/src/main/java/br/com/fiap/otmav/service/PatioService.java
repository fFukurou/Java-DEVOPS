package br.com.fiap.otmav.service;

import br.com.fiap.otmav.domain.patio.*;
import br.com.fiap.otmav.domain.filial.FilialRepository;
import br.com.fiap.otmav.domain.regiao.RegiaoRepository;
import br.com.fiap.otmav.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatioService {

    @Autowired
    private final PatioRepository patioRepository;

    @Autowired
    private final FilialRepository filialRepository;

    @Autowired
    private final RegiaoRepository regiaoRepository;

    @Transactional
    public ReadPatioDto create(CreatePatioDto dto) {
        Patio p = new Patio();
        p.setTotalMotos(dto.totalMotos());
        p.setCapacidadeMoto(dto.capacidadeMoto());

        if (dto.filialId() != null) {
            var filial = filialRepository.findById(dto.filialId())
                    .orElseThrow(() -> new NotFoundException("Filial not found with id: " + dto.filialId()));
            p.setFilial(filial);
        }
        if (dto.regiaoId() != null) {
            var regiao = regiaoRepository.findById(dto.regiaoId())
                    .orElseThrow(() -> new NotFoundException("Regiao not found with id: " + dto.regiaoId()));
            p.setRegiao(regiao);
        }

        return new ReadPatioDto(patioRepository.save(p));
    }

    public Page<ReadPatioDto> findAllFiltered(Integer totalMotos, Integer capacidadeMoto, Long filialId, Long regiaoId, Pageable pageable) {
        return patioRepository.findAllFiltered(totalMotos, capacidadeMoto, filialId, regiaoId, pageable)
                .map(ReadPatioDto::new);
    }

    public ReadPatioDto findById(Long id) {
        return patioRepository.findById(id)
                .map(ReadPatioDto::new)
                .orElseThrow(() -> new NotFoundException("Patio not found with id: " + id));
    }

    @Transactional
    public ReadPatioDto update(Long id, UpdatePatioDto dto) {
        Patio p = patioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patio not found with id: " + id));

        if (dto.totalMotos() != null) p.setTotalMotos(dto.totalMotos());
        if (dto.capacidadeMoto() != null) p.setCapacidadeMoto(dto.capacidadeMoto());

        if (dto.filialId() != null) {
            var filial = filialRepository.findById(dto.filialId())
                    .orElseThrow(() -> new NotFoundException("Filial not found with id: " + dto.filialId()));
            p.setFilial(filial);
        }
        if (dto.regiaoId() != null) {
            var regiao = regiaoRepository.findById(dto.regiaoId())
                    .orElseThrow(() -> new NotFoundException("Regiao not found with id: " + dto.regiaoId()));
            p.setRegiao(regiao);
        }

        return new ReadPatioDto(patioRepository.save(p));
    }

    @Transactional
    public void delete(Long id) {
        if (!patioRepository.existsById(id)) {
            throw new NotFoundException("Patio not found with id: " + id);
        }
        patioRepository.deleteById(id);
    }
}
