package fiap.com.sensorium.domain.moto;

public record CreateMotoDto(
        String placa,
        String chassi,
        String condicao,
        String latitude,
        String longitude,
        Long motoristaId,
        Long modeloId,
        Long setorId,
        Long situacaoId
) {

}
