package fiap.com.sensorium.domain.endereco;

public record EnderecoDto(
        Long id,
        Integer numero,
        String estado,
        String codigoPais,
        String codigoPostal,
        String complemento,
        String rua
) {
    public EnderecoDto(Endereco endereco) {
        this(
                endereco.getId(),
                endereco.getNumero(),
                endereco.getEstado(),
                endereco.getCodigoPais(),
                endereco.getCep(),
                endereco.getComplemento(),
                endereco.getRua()
        );
    }
}
