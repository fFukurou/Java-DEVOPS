package br.com.fiap.otmav.service;

import br.com.fiap.otmav.domain.dados.Dados;
import br.com.fiap.otmav.domain.dados.DadosRepository;
import br.com.fiap.otmav.domain.motorista.*;
import br.com.fiap.otmav.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MotoristaService {

    @Autowired
    private final MotoristaRepository motoristaRepository;

    @Autowired
    private final DadosRepository dadosRepository;

    @Transactional
    public ReadMotoristaDto create(CreateMotoristaDto dto) {
        Dados dados = dadosRepository.findById(dto.dadosId())
                .orElseThrow(() -> new NotFoundException("Dados nao encontrados com id: " + dto.dadosId()));

        Motorista m = new Motorista();
        m.setPlano(dto.plano());
        m.setDados(dados);

        return new ReadMotoristaDto(motoristaRepository.save(m));
    }

    public Page<ReadMotoristaDto> findAllFiltered(MotoristaPlano plano, Long dadosId, Pageable pageable) {
        return motoristaRepository.findAllFiltered(plano, dadosId, pageable)
                .map(ReadMotoristaDto::new);
    }

    public ReadMotoristaDto findById(Long id) {
        return motoristaRepository.findById(id)
                .map(ReadMotoristaDto::new)
                .orElseThrow(() -> new NotFoundException("Motorista nao encontrado com id: " + id));
    }

    @Transactional
    public ReadMotoristaDto update(Long id, UpdateMotoristaDto dto) {
        Motorista m = motoristaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Motorista nao encontrado com id: " + id));

        if (dto.plano() != null) m.setPlano(dto.plano());
        if (dto.dadosId() != null) {
            Dados dados = dadosRepository.findById(dto.dadosId())
                    .orElseThrow(() -> new NotFoundException("Dados nao encontrados com id: " + dto.dadosId()));
            m.setDados(dados);
        }

        return new ReadMotoristaDto(motoristaRepository.save(m));
    }

    @Transactional
    public void delete(Long id) {
        if (!motoristaRepository.existsById(id)) {
            throw new NotFoundException("Motorista nao encontrados com id: " + id);
        }
        motoristaRepository.deleteById(id);
    }
}
