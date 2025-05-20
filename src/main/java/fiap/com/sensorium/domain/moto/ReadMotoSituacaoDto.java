package fiap.com.sensorium.domain.moto;

public record ReadMotoSituacaoDto(
        Long id,
        String placa,
        String condicao
) {
    public ReadMotoSituacaoDto(Moto moto) {
        this(moto.getId(), moto.getPlaca(), moto.getCondicao());
    }
}