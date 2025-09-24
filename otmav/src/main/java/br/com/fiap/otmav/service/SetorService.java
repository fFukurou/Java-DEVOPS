package br.com.fiap.otmav.service;

import br.com.fiap.otmav.domain.setor.*;
import br.com.fiap.otmav.domain.patio.PatioRepository;
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
public class SetorService {

    @Autowired
    private final SetorRepository setorRepository;

    @Autowired
    private final PatioRepository patioRepository;

    @Autowired
    private final RegiaoRepository regiaoRepository;

    @Transactional
    public ReadSetorDto create(CreateSetorDto dto) {
        Setor s = new Setor();
        s.setQtdMoto(dto.qtdMoto());
        s.setCapacidade(dto.capacidade());
        s.setNomeSetor(dto.nomeSetor());
        s.setDescricao(dto.descricao());
        s.setCor(dto.cor());

        if (dto.patioId() != null) {
            var patio = patioRepository.findById(dto.patioId())
                    .orElseThrow(() -> new NotFoundException("Patio not found with id: " + dto.patioId()));
            s.setPatio(patio);
        }
        if (dto.regiaoId() != null) {
            var regiao = regiaoRepository.findById(dto.regiaoId())
                    .orElseThrow(() -> new NotFoundException("Regiao not found with id: " + dto.regiaoId()));
            s.setRegiao(regiao);
        }
        return new ReadSetorDto(setorRepository.save(s));
    }

    public Page<ReadSetorDto> findAllFiltered(Integer qtdMoto, Integer capacidade, String nome, String cor, Long patioId, Long regiaoId, Pageable pageable) {
        return setorRepository.findAllFiltered(qtdMoto, capacidade, nome, cor, patioId, regiaoId, pageable)
                .map(ReadSetorDto::new);
    }

    public ReadSetorDto findById(Long id) {
        return setorRepository.findById(id)
                .map(ReadSetorDto::new)
                .orElseThrow(() -> new NotFoundException("Setor not found with id: " + id));
    }

    @Transactional
    public ReadSetorDto update(Long id, UpdateSetorDto dto) {
        Setor s = setorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Setor not found with id: " + id));

        if (dto.qtdMoto() != null) s.setQtdMoto(dto.qtdMoto());
        if (dto.capacidade() != null) s.setCapacidade(dto.capacidade());
        if (dto.nomeSetor() != null) s.setNomeSetor(dto.nomeSetor());
        if (dto.descricao() != null) s.setDescricao(dto.descricao());
        if (dto.cor() != null) s.setCor(dto.cor());

        if (dto.patioId() != null) {
            var patio = patioRepository.findById(dto.patioId())
                    .orElseThrow(() -> new NotFoundException("Patio not found with id: " + dto.patioId()));
            s.setPatio(patio);
        }
        if (dto.regiaoId() != null) {
            var regiao = regiaoRepository.findById(dto.regiaoId())
                    .orElseThrow(() -> new NotFoundException("Regiao not found with id: " + dto.regiaoId()));
            s.setRegiao(regiao);
        }

        return new ReadSetorDto(setorRepository.save(s));
    }

    @Transactional
    public void delete(Long id) {
        if (!setorRepository.existsById(id)) {
            throw new NotFoundException("Setor not found with id: " + id);
        }
        setorRepository.deleteById(id);
    }
}
