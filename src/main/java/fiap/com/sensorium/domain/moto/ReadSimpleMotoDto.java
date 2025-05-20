package fiap.com.sensorium.domain.moto;

public record ReadSimpleMotoDto(
        Long id,
        String placa,
        String condicao
) {
    public ReadSimpleMotoDto(Moto moto) {
        this(moto.getId(), moto.getPlaca(), moto.getCondicao());
    }
}