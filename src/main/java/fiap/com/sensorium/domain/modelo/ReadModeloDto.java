package fiap.com.sensorium.domain.modelo;

import fiap.com.sensorium.domain.moto.ReadSimpleMotoDto;

import java.util.List;

public record ReadModeloDto(
        Long id,
        String nome,
        String frenagem,
        String sistemaPartida,
        Integer tanque,
        String combustivel,
        Integer consumo,
        List<ReadSimpleMotoDto> motos
) {
    public ReadModeloDto(Modelo modelo) {
        this(
                modelo.getId(),
                modelo.getNome(),
                modelo.getFrenagem(),
                modelo.getSistemaPartida(),
                modelo.getTanque(),
                modelo.getCombustivel(),
                modelo.getConsumo(),
                modelo.getMotos().stream()
                        .map(ReadSimpleMotoDto::new)
                        .toList()
        );
    }
}
