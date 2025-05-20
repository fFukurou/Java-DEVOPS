package fiap.com.sensorium.domain.motorista;

public record ReadMotoristaDto(
        Long id,
        String plano,
        String nome  // From Dados
) {
    public ReadMotoristaDto(Motorista motorista) {
        this(
                motorista.getId(),
                motorista.getPlano(),
                motorista.getDados().getNome()
        );
    }
}
