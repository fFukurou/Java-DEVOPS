package br.com.fiap.otmav.service;

import br.com.fiap.otmav.domain.moto.*;
import br.com.fiap.otmav.domain.motorista.MotoristaRepository;
import br.com.fiap.otmav.domain.modelo.ModeloRepository;
import br.com.fiap.otmav.domain.setor.SetorRepository;
import br.com.fiap.otmav.domain.situacao.SituacaoRepository;
import br.com.fiap.otmav.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MotoService {

    @Autowired
    private final MotoRepository motoRepository;

    @Autowired
    private final MotoristaRepository motoristaRepository;

    @Autowired
    private final ModeloRepository modeloRepository;

    @Autowired
    private final SetorRepository setorRepository;

    @Autowired
    private final SituacaoRepository situacaoRepository;

    @Transactional
    public ReadMotoDto create(CreateMotoDto dto) {
        Moto m = new Moto();
        m.setPlaca(dto.placa());
        m.setChassi(dto.chassi());
        m.setCondicao(dto.condicao());
        m.setLocalizacaoWkt(dto.localizacao());

        if (dto.motoristaId() != null) {
            var motorista = motoristaRepository.findById(dto.motoristaId())
                    .orElseThrow(() -> new NotFoundException("Motorista not found with id: " + dto.motoristaId()));
            m.setMotorista(motorista);
        }
        if (dto.modeloId() != null) {
            var modelo = modeloRepository.findById(dto.modeloId())
                    .orElseThrow(() -> new NotFoundException("Modelo not found with id: " + dto.modeloId()));
            m.setModelo(modelo);
        }
        if (dto.setorId() != null) {
            var setor = setorRepository.findById(dto.setorId())
                    .orElseThrow(() -> new NotFoundException("Setor not found with id: " + dto.setorId()));
            m.setSetor(setor);
        }
        if (dto.situacaoId() != null) {
            var situacao = situacaoRepository.findById(dto.situacaoId())
                    .orElseThrow(() -> new NotFoundException("Situacao not found with id: " + dto.situacaoId()));
            m.setSituacao(situacao);
        }

        return new ReadMotoDto(motoRepository.save(m));
    }

    public Page<ReadMotoDto> findAllFiltered(String placa, String chassi, String condicao, Long motoristaId, Long modeloId, Long setorId, Long situacaoId, Pageable pageable) {
        return motoRepository.findAllFiltered(placa, chassi, condicao, motoristaId, modeloId, setorId, situacaoId, pageable)
                .map(ReadMotoDto::new);
    }

    public ReadMotoDto findById(Long id) {
        return motoRepository.findById(id)
                .map(ReadMotoDto::new)
                .orElseThrow(() -> new NotFoundException("Moto not found with id: " + id));
    }

    @Transactional
    public ReadMotoDto update(Long id, UpdateMotoDto dto) {
        Moto m = motoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Moto not found with id: " + id));

        if (dto.placa() != null) m.setPlaca(dto.placa());
        if (dto.chassi() != null) m.setChassi(dto.chassi());
        if (dto.condicao() != null) m.setCondicao(dto.condicao());
        if (dto.localizacao() != null) m.setLocalizacaoWkt(dto.localizacao());

        if (dto.motoristaId() != null) {
            var motorista = motoristaRepository.findById(dto.motoristaId())
                    .orElseThrow(() -> new NotFoundException("Motorista not found with id: " + dto.motoristaId()));
            m.setMotorista(motorista);
        }
        if (dto.modeloId() != null) {
            var modelo = modeloRepository.findById(dto.modeloId())
                    .orElseThrow(() -> new NotFoundException("Modelo not found with id: " + dto.modeloId()));
            m.setModelo(modelo);
        }
        if (dto.setorId() != null) {
            var setor = setorRepository.findById(dto.setorId())
                    .orElseThrow(() -> new NotFoundException("Setor not found with id: " + dto.setorId()));
            m.setSetor(setor);
        }
        if (dto.situacaoId() != null) {
            var situacao = situacaoRepository.findById(dto.situacaoId())
                    .orElseThrow(() -> new NotFoundException("Situacao not found with id: " + dto.situacaoId()));
            m.setSituacao(situacao);
        }

        return new ReadMotoDto(motoRepository.save(m));
    }

    @Transactional
    public void delete(Long id) {
        if (!motoRepository.existsById(id)) {
            throw new NotFoundException("Moto not found with id: " + id);
        }
        motoRepository.deleteById(id);
    }
}
