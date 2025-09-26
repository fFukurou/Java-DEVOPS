package br.com.fiap.otmav.domain.funcionario;

import br.com.fiap.otmav.domain.dados.ReadDadosDto;
import br.com.fiap.otmav.domain.filial.ReadFilialDto;

public record ReadFuncionarioDto(
        Long id,
        String cargo,
        ReadDadosDto dados,
        ReadFilialDto filial
) {
    public ReadFuncionarioDto(Funcionario f) {
        this(
                f.getId(),
                f.getCargo(),
                f.getDados() != null ? new ReadDadosDto(f.getDados()) : null,
                f.getFilial() != null ? new ReadFilialDto(f.getFilial()) : null
        );
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String cargo() {
        return cargo;
    }

    @Override
    public ReadDadosDto dados() {
        return dados;
    }

    @Override
    public ReadFilialDto filial() {
        return filial;
    }
}
