package fiap.com.sensorium.domain.funcionario;

public record ReadSimpleFuncionarioDto(Long id,
                                       String cargo,
                                       String nome) {

    public ReadSimpleFuncionarioDto(Funcionario funcionario) {
        this(
                funcionario.getId(),
                funcionario.getCargo(),
                funcionario.getDados().getNome()
        );
    }
}
