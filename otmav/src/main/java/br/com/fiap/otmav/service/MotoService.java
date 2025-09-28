package br.com.fiap.otmav.service;

import br.com.fiap.otmav.domain.moto.*;
import br.com.fiap.otmav.domain.motorista.MotoristaRepository;
import br.com.fiap.otmav.domain.modelo.ModeloRepository;
import br.com.fiap.otmav.domain.setor.Setor;
import br.com.fiap.otmav.domain.setor.SetorRepository;
import br.com.fiap.otmav.domain.situacao.SituacaoRepository;
import br.com.fiap.otmav.exception.DuplicateEntryException;
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
        if (dto.placa() != null && motoRepository.existsByPlaca(dto.placa())) {
            throw new DuplicateEntryException("Placa ja em uso: " + dto.placa());
        }

        Moto m = new Moto();
        m.setPlaca(dto.placa());
        m.setChassi(dto.chassi());
        m.setCondicao(dto.condicao());
        m.setLocalizacaoWkt(dto.localizacao());

        if (dto.motoristaId() != null) {
            var motorista = motoristaRepository.findById(dto.motoristaId())
                    .orElseThrow(() -> new NotFoundException("Motorista nao encontrado com id: " + dto.motoristaId()));
            m.setMotorista(motorista);
        }
        if (dto.modeloId() != null) {
            var modelo = modeloRepository.findById(dto.modeloId())
                    .orElseThrow(() -> new NotFoundException("Modelo nao encontrado com id: " + dto.modeloId()));
            m.setModelo(modelo);
        }
        if (dto.setorId() != null) {
            var setor = setorRepository.findById(dto.setorId())
                    .orElseThrow(() -> new NotFoundException("Setor nao encontrado com id: " + dto.setorId()));

            if (setor.getQtdMoto() != null && setor.getCapacidade() != null && setor.getQtdMoto() >= setor.getCapacidade()) {
                throw new IllegalStateException("Setor cheio");
            }

            setor.setQtdMoto((setor.getQtdMoto() == null ? 0 : setor.getQtdMoto()) + 1);
            m.setSetor(setor);
        }
        if (dto.situacaoId() != null) {
            var situacao = situacaoRepository.findById(dto.situacaoId())
                    .orElseThrow(() -> new NotFoundException("Situacao nao encontrada com id: " + dto.situacaoId()));
            m.setSituacao(situacao);
        }

        if (m.getSetor() != null) setorRepository.save(m.getSetor());

        return new ReadMotoDto(motoRepository.save(m));
    }

    public Page<ReadMotoDto> findAllFiltered(String placa, String chassi, String condicao, Long motoristaId, Long modeloId, Long setorId, Long situacaoId, Pageable pageable) {
        return motoRepository.findAllFiltered(placa, chassi, condicao, motoristaId, modeloId, setorId, situacaoId, pageable)
                .map(ReadMotoDto::new);
    }

    public ReadMotoDto findById(Long id) {
        return motoRepository.findById(id)
                .map(ReadMotoDto::new)
                .orElseThrow(() -> new NotFoundException("Moto nao encontrada com id: " + id));
    }

    @Transactional
    public ReadMotoDto update(Long id, UpdateMotoDto dto) {
        Moto m = motoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Moto nao encontrada com id: " + id));

        if (dto.placa() != null) {
            if (motoRepository.existsByPlacaAndIdNot(dto.placa(), id)) {
                throw new DuplicateEntryException("Placa ja em uso por outra moto: " + dto.placa());
            }
            m.setPlaca(dto.placa());
        }
        if (dto.chassi() != null) m.setChassi(dto.chassi());
        if (dto.condicao() != null) m.setCondicao(dto.condicao());
        if (dto.localizacao() != null) m.setLocalizacaoWkt(dto.localizacao());

        if (dto.motoristaId() != null) {
            var motorista = motoristaRepository.findById(dto.motoristaId())
                    .orElseThrow(() -> new NotFoundException("Motorista nao encontrado com id: " + dto.motoristaId()));
            m.setMotorista(motorista);
        }
        if (dto.modeloId() != null) {
            var modelo = modeloRepository.findById(dto.modeloId())
                    .orElseThrow(() -> new NotFoundException("Modelo nao encontrado com id: " + dto.modeloId()));
            m.setModelo(modelo);
        }
        if (dto.setorId() != null) {
            var setor = setorRepository.findById(dto.setorId())
                    .orElseThrow(() -> new NotFoundException("Setor nao encontrado com id: " + dto.setorId()));

            if (setor.getQtdMoto() != null && setor.getCapacidade() != null && setor.getQtdMoto() >= setor.getCapacidade()) {
                throw new IllegalStateException("Setor cheio");
            }
            var oldSetor = m.getSetor();
            if (oldSetor != null) {
                oldSetor.setQtdMoto(Math.max(0, (oldSetor.getQtdMoto() == null ? 0 : oldSetor.getQtdMoto()) - 1));
                setorRepository.save(oldSetor);
            }
            setor.setQtdMoto((setor.getQtdMoto() == null ? 0 : setor.getQtdMoto()) + 1);
            setorRepository.save(setor);
            m.setSetor(setor);
        }
        if (dto.situacaoId() != null) {
            var situacao = situacaoRepository.findById(dto.situacaoId())
                    .orElseThrow(() -> new NotFoundException("Situacao nao encontrada com id: " + dto.situacaoId()));
            m.setSituacao(situacao);
        }

        return new ReadMotoDto(motoRepository.save(m));
    }

    @Transactional
    public void delete(Long id) {
        if (!motoRepository.existsById(id)) {
            throw new NotFoundException("Moto nao encontrada com id: " + id);
        }
        motoRepository.deleteById(id);
    }


    @Transactional
    public ReadMotoDto transferToSetor(Long motoId, TransferSetorDto dto) {
        Moto moto = motoRepository.findById(motoId)
                .orElseThrow(() -> new NotFoundException("Moto nao encontrada com id: " + motoId));

        Setor target = setorRepository.findById(dto.setorId())
                .orElseThrow(() -> new NotFoundException("Setor nao encontrado com id: " + dto.setorId()));

        if (moto.getSetor() != null && moto.getSetor().getId().equals(target.getId())) {
            throw new IllegalArgumentException("Moto já est[a nesse setor");
        }

        Integer targetQtd = target.getQtdMoto() == null ? 0 : target.getQtdMoto();
        Integer targetCap = target.getCapacidade() == null ? 0 : target.getCapacidade();
        if (targetCap > 0 && targetQtd >= targetCap) {
            throw new IllegalStateException("Setor cheio");
        }

        var oldSetor = moto.getSetor();
        if (oldSetor != null) {
            oldSetor.setQtdMoto(Math.max(0, (oldSetor.getQtdMoto() == null ? 0 : oldSetor.getQtdMoto()) - 1));
            setorRepository.save(oldSetor);
        }

        target.setQtdMoto(targetQtd + 1);
        setorRepository.save(target);

        moto.setSetor(target);
        return new ReadMotoDto(motoRepository.save(moto));
    }


    @Transactional
    public ReadMotoDto assignDriver(Long motoId, AssignMotoristaDto dto) {
        Moto moto = motoRepository.findById(motoId)
                .orElseThrow(() -> new NotFoundException("Moto nao encontrada com id : " + motoId));

        var motorista = motoristaRepository.findById(dto.motoristaId())
                .orElseThrow(() -> new NotFoundException("Motorista nao encontrado com id: " + dto.motoristaId()));

        moto.setMotorista(motorista);
        return new ReadMotoDto(motoRepository.save(moto));
    }
}
