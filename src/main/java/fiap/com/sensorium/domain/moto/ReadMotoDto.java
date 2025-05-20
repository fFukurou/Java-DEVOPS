package fiap.com.sensorium.domain.moto;

import fiap.com.sensorium.domain.modelo.Modelo;
import fiap.com.sensorium.domain.motorista.Motorista;
import fiap.com.sensorium.domain.setor.Setor;

public record ReadMotoDto(
        Long id,
        String placa,
        String chassi,
        String condicao,
        String latitude,
        String longitude,
        Long motoristaId,
        String motoristaNome,
        Long modeloId,
        String modeloNome,
        Long setorId,
        String setorNome,
        Long situacaoId,
        String situacaoNome
) {
    public ReadMotoDto(Moto moto) {
        this(
                moto.getId(),
                moto.getPlaca(),
                moto.getChassi(),
                moto.getCondicao(),
                moto.getLatitude(),
                moto.getLongitude(),
                moto.getMotorista() != null ? moto.getMotorista().getId() : null,
                moto.getMotorista() != null ? moto.getMotorista().getDados().getNome() : null,
                moto.getModelo() != null ? moto.getModelo().getId() : null,
                moto.getModelo() != null ? moto.getModelo().getNome() : null,
                moto.getSetor() != null ? moto.getSetor().getId() : null,
                moto.getSetor() != null ? moto.getSetor().getNome() : null,
                moto.getSituacao() != null ? moto.getSituacao().getId() : null,
                moto.getSituacao() != null ? moto.getSituacao().getNome() : null
        );
    }
}

