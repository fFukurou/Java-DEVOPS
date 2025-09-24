package br.com.fiap.otmav.service;

import br.com.fiap.otmav.domain.situacao.*;
import br.com.fiap.otmav.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SituacaoService {

    @Autowired
    private final SituacaoRepository situacaoRepository;

    @Transactional
    public ReadSituacaoDto create(CreateSituacaoDto dto) {
        Situacao s = new Situacao();
        s.setNome(dto.nome());
        s.setDescricao(dto.descricao());
        s.setStatus(dto.status());
        return new ReadSituacaoDto(situacaoRepository.save(s));
    }

    public Page<ReadSituacaoDto> findAllFiltered(String nome, SituacaoStatus status, Pageable pageable) {
        return situacaoRepository.findAllFiltered(nome, status, pageable)
                .map(ReadSituacaoDto::new);
    }

    public ReadSituacaoDto findById(Long id) {
        return situacaoRepository.findById(id)
                .map(ReadSituacaoDto::new)
                .orElseThrow(() -> new NotFoundException("Situacao not found with id: " + id));
    }

    @Transactional
    public ReadSituacaoDto update(Long id, UpdateSituacaoDto dto) {
        Situacao s = situacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Situacao not found with id: " + id));

        if (dto.nome() != null) s.setNome(dto.nome());
        if (dto.descricao() != null) s.setDescricao(dto.descricao());
        if (dto.status() != null) s.setStatus(dto.status());

        return new ReadSituacaoDto(situacaoRepository.save(s));
    }

    @Transactional
    public void delete(Long id) {
        if (!situacaoRepository.existsById(id)) {
            throw new NotFoundException("Situacao not found with id: " + id);
        }
        situacaoRepository.deleteById(id);
    }
}
