package br.com.fiap.otmav.service;

import br.com.fiap.otmav.domain.regiao.*;
import br.com.fiap.otmav.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegiaoService {

    @Autowired
    private final RegiaoRepository regiaoRepository;

    @Transactional
    public ReadRegiaoDto create(CreateRegiaoDto dto) {
        Regiao r = new Regiao();
        r.setLocalizacaoWkt(dto.localizacao());
        r.setArea(dto.area());
        return new ReadRegiaoDto(regiaoRepository.save(r));
    }

    public Page<ReadRegiaoDto> findAllFiltered(Double area, String searchWkt, Pageable pageable) {
        return regiaoRepository.findAllFiltered(area, searchWkt, pageable)
                .map(ReadRegiaoDto::new);
    }

    public ReadRegiaoDto findById(Long id) {
        return regiaoRepository.findById(id)
                .map(ReadRegiaoDto::new)
                .orElseThrow(() -> new NotFoundException("Regiao not found with id: " + id));
    }

    @Transactional
    public ReadRegiaoDto update(Long id, UpdateRegiaoDto dto) {
        Regiao r = regiaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Regiao not found with id: " + id));
        if (dto.localizacao() != null) r.setLocalizacaoWkt(dto.localizacao());
        if (dto.area() != null) r.setArea(dto.area());
        return new ReadRegiaoDto(regiaoRepository.save(r));
    }

    @Transactional
    public void delete(Long id) {
        if (!regiaoRepository.existsById(id)) {
            throw new NotFoundException("Regiao not found with id: " + id);
        }
        regiaoRepository.deleteById(id);
    }
}
