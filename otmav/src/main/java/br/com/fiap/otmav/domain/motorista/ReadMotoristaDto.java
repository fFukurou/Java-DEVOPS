package br.com.fiap.otmav.domain.motorista;

import br.com.fiap.otmav.domain.dados.ReadDadosDto;

public record ReadMotoristaDto(
        Long id,
        MotoristaPlano plano,
        ReadDadosDto dados
) {
    public ReadMotoristaDto(Motorista m) {
        this(m.getId(), m.getPlano(), m.getDados() != null ? new ReadDadosDto(m.getDados()) : null);
    }
}
