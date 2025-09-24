package br.com.fiap.otmav.domain.modelo;

public record ReadModeloDto(
        Long id,
        String nomeModelo,
        String frenagem,
        String sisPartida,
        Integer tanque,
        String tipoCombustivel,
        Integer consumo
) {
    public ReadModeloDto(Modelo m) {
        this(m.getId(), m.getNomeModelo(), m.getFrenagem(), m.getSisPartida(), m.getTanque(), m.getTipoCombustivel(), m.getConsumo());
    }
}
