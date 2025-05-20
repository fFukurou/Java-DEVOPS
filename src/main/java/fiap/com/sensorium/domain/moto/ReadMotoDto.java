package fiap.com.sensorium.domain.moto;

public record ReadMotoDto(
        Long id,
        String placa,
        String condicao
) {
    public ReadMotoDto(Moto moto) {
        this(moto.getId(), moto.getPlaca(), moto.getCondicao());
    }
}