package br.com.fiap.otmav.domain.setor;

import br.com.fiap.otmav.domain.patio.ReadPatioDto;
import br.com.fiap.otmav.domain.regiao.ReadRegiaoDto;

public record ReadSetorDto(
        Long id,
        Integer qtdMoto,
        Integer capacidade,
        String nomeSetor,
        String descricao,
        String cor,
        ReadPatioDto patio,
        ReadRegiaoDto regiao
) {
    public ReadSetorDto(Setor s) {
        this(
            s.getId(),
            s.getQtdMoto(),
            s.getCapacidade(),
            s.getNomeSetor(),
            s.getDescricao(),
            s.getCor(),
            s.getPatio() != null ? new ReadPatioDto(s.getPatio()) : null,
            s.getRegiao() != null ? new ReadRegiaoDto(s.getRegiao()) : null
        );
    }
}
