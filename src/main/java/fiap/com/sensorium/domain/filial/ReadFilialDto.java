package fiap.com.sensorium.domain.filial;

import fiap.com.sensorium.domain.endereco.EnderecoDto;
import fiap.com.sensorium.domain.funcionario.ReadSimpleFuncionarioDto;
import fiap.com.sensorium.domain.patio.ReadPatioDto;

import java.util.List;

public record ReadFilialDto(
        Long id,
        String nomeFilial,
        EnderecoDto endereco,
        ReadSimpleFuncionarioDto funcionario,
        List<ReadPatioDto> patios
) {
    public ReadFilialDto(Filial filial) {
        this(
                filial.getId(),
                filial.getNomeFilial(),
                new EnderecoDto(filial.getEndereco()),
                new ReadSimpleFuncionarioDto(filial.getFuncionario()),
                filial.getPatios().stream()
                        .map(ReadPatioDto::new)
                        .toList()
        );
    }
}
