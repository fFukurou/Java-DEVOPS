package fiap.com.sensorium.domain.situacao;

import fiap.com.sensorium.domain.moto.ReadSimpleMotoDto;

import java.util.List;

public record ReadSituacaoDto(Long id, String nome, String descricao, String status, List<ReadSimpleMotoDto> motos) {

    public ReadSituacaoDto(Situacao situacao) {
        this(situacao.getId(),
            situacao.getNome(),
            situacao.getDescricao(),
            situacao.getStatus(),
            situacao.getMotos().stream().map(ReadSimpleMotoDto::new).toList());
    }
}
