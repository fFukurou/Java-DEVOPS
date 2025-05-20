package fiap.com.sensorium.domain.funcionario;

import fiap.com.sensorium.domain.filial.ReadSimpleFilialDto;

import java.util.List;

public record ReadFuncionarioDto(
        Long id,
        String cargo,
        String nome,  // FROM DADOS
        List<ReadSimpleFilialDto> filiais
) {
    public ReadFuncionarioDto(Funcionario funcionario) {
        this(
                funcionario.getId(),
                funcionario.getCargo(),
                funcionario.getDados().getNome(),
                funcionario.getFiliais().stream()
                        .map(ReadSimpleFilialDto::new)
                        .toList()
        );
    }
}