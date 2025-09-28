package br.com.fiap.otmav.service;

import br.com.fiap.otmav.domain.modelo.*;
import br.com.fiap.otmav.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModeloService {

    @Autowired
    private final ModeloRepository modeloRepository;

    @Transactional
    public ReadModeloDto create(CreateModeloDto dto) {
        Modelo m = new Modelo();
        m.setNomeModelo(dto.nomeModelo());
        m.setFrenagem(dto.frenagem());
        m.setSisPartida(dto.sisPartida());
        m.setTanque(dto.tanque());
        m.setTipoCombustivel(dto.tipoCombustivel());
        m.setConsumo(dto.consumo());

        return new ReadModeloDto(modeloRepository.save(m));
    }

    public Page<ReadModeloDto> findAllFiltered(String nome, String tipoCombustivel, Integer tanque, Pageable pageable) {
        return modeloRepository.findAllFiltered(nome, tipoCombustivel, tanque, pageable)
                .map(ReadModeloDto::new);
    }

    public ReadModeloDto findById(Long id) {
        return modeloRepository.findById(id)
                .map(ReadModeloDto::new)
                .orElseThrow(() -> new NotFoundException("Modelo nao encontrado com id: " + id));
    }

    @Transactional
    public ReadModeloDto update(Long id, UpdateModeloDto dto) {
        Modelo m = modeloRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Modelo nao encontrado com id: " + id));

        if (dto.nomeModelo() != null) m.setNomeModelo(dto.nomeModelo());
        if (dto.frenagem() != null) m.setFrenagem(dto.frenagem());
        if (dto.sisPartida() != null) m.setSisPartida(dto.sisPartida());
        if (dto.tanque() != null) m.setTanque(dto.tanque());
        if (dto.tipoCombustivel() != null) m.setTipoCombustivel(dto.tipoCombustivel());
        if (dto.consumo() != null) m.setConsumo(dto.consumo());

        return new ReadModeloDto(modeloRepository.save(m));
    }

    @Transactional
    public void delete(Long id) {
        if (!modeloRepository.existsById(id)) {
            throw new NotFoundException("Modelo nao encontrado com id: " + id);
        }
        modeloRepository.deleteById(id);
    }
}
