package br.com.fiap.otmav.domain.patio;

import br.com.fiap.otmav.domain.filial.ReadFilialDto;
import br.com.fiap.otmav.domain.regiao.ReadRegiaoDto;

public record ReadPatioDto(
        Long id,
        Integer totalMotos,
        Integer capacidadeMoto,
        ReadFilialDto filial,
        ReadRegiaoDto regiao
) {
    public ReadPatioDto(Patio p) {
        this(
            p.getId(),
            p.getTotalMotos(),
            p.getCapacidadeMoto(),
            p.getFilial() != null ? new ReadFilialDto(p.getFilial()) : null,
            p.getRegiao() != null ? new ReadRegiaoDto(p.getRegiao()) : null
        );
    }
}
