package fiap.com.sensorium.domain.filial;

public record ReadSimpleFilialDto(
        Long id,
        String nomeFilial
) {
    public ReadSimpleFilialDto(Filial filial) {
        this(
                filial.getId(),
                filial.getNome()
            );
        }
}
