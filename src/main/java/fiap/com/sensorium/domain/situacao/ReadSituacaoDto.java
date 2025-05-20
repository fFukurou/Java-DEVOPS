package fiap.com.sensorium.domain.situacao;

import fiap.com.sensorium.domain.moto.Moto;
import fiap.com.sensorium.domain.moto.ReadMotoDto;

import java.util.List;

public record ReadSituacaoDto(Long id, String nome, String descricao, String status, List<ReadMotoDto> motos) {

    public ReadSituacaoDto(Situacao situacao) {
        this(situacao.getId(),
            situacao.getNome(),
            situacao.getDescricao(),
            situacao.getStatus(),
            situacao.getMotos().stream().map(ReadMotoDto::new).toList());
    }
}
