package br.com.fiap.otmav.domain.moto;

import br.com.fiap.otmav.domain.motorista.ReadMotoristaDto;
import br.com.fiap.otmav.domain.modelo.ReadModeloDto;
import br.com.fiap.otmav.domain.setor.ReadSetorDto;
import br.com.fiap.otmav.domain.situacao.ReadSituacaoDto;

public record ReadMotoDto(
        Long id,
        String placa,
        String chassi,
        String condicao,
        String localizacao,
        ReadMotoristaDto motorista,
        ReadModeloDto modelo,
        ReadSetorDto setor,
        ReadSituacaoDto situacao
) {
    public ReadMotoDto(Moto m) {
        this(
            m.getId(),
            m.getPlaca(),
            m.getChassi(),
            m.getCondicao(),
            m.getLocalizacaoWkt(),
            m.getMotorista() != null ? new ReadMotoristaDto(m.getMotorista()) : null,
            m.getModelo() != null ? new ReadModeloDto(m.getModelo()) : null,
            m.getSetor() != null ? new ReadSetorDto(m.getSetor()) : null,
            m.getSituacao() != null ? new ReadSituacaoDto(m.getSituacao()) : null
        );
    }
}
