package fiap.com.sensorium.service;

import fiap.com.sensorium.domain.modelo.ModeloRepository;
import fiap.com.sensorium.domain.moto.CreateMotoDto;
import fiap.com.sensorium.domain.moto.Moto;
import fiap.com.sensorium.domain.moto.MotoRepository;
import fiap.com.sensorium.domain.moto.UpdateMotoDto;
import fiap.com.sensorium.domain.motorista.MotoristaRepository;
import fiap.com.sensorium.domain.setor.SetorRepository;
import fiap.com.sensorium.domain.situacao.SituacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;
    @Autowired
    private MotoristaRepository motoristaRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private SituacaoRepository situacaoRepository;

    public void updateMotoFromDto(Moto moto, Object dto) {
        if (dto instanceof CreateMotoDto createDto) {
            moto.setPlaca(createDto.placa());
            moto.setChassi(createDto.chassi());
            moto.setCondicao(createDto.condicao());
            moto.setLatitude(createDto.latitude());
            moto.setLongitude(createDto.longitude());
            setRelationships(moto, createDto.motoristaId(), createDto.modeloId(),
                    createDto.setorId(), createDto.situacaoId());
        }

        if (dto instanceof UpdateMotoDto updateDto) {
            moto.setPlaca(updateDto.placa());
            moto.setChassi(updateDto.chassi());
            moto.setCondicao(updateDto.condicao());
            moto.setLatitude(updateDto.latitude());
            moto.setLongitude(updateDto.longitude());
            setRelationships(moto, updateDto.motoristaId(), updateDto.modeloId(),
                    updateDto.setorId(), updateDto.situacaoId());
        }


    }

    public void setRelationships(Moto moto, Long motoristaId, Long modeloId,
                                  Long setorId, Long situacaoId) {
        if (motoristaId != null) {
            motoristaRepository.findById(motoristaId).ifPresent(moto::setMotorista);
        } else {
            moto.setMotorista(null);
        }

        if (modeloId != null) {
            modeloRepository.findById(modeloId).ifPresent(moto::setModelo);
        }

        if (setorId != null) {
            setorRepository.findById(setorId).ifPresent(moto::setSetor);
        }

        if (situacaoId != null) {
            situacaoRepository.findById(situacaoId).ifPresent(moto::setSituacao);
        }
    }
}
