package br.com.fiap.otmav.domain.filial;

import br.com.fiap.otmav.domain.endereco.ReadEnderecoDto;

public record ReadFilialDto(
        Long id,
        String nome,
        ReadEnderecoDto endereco
) {
    public ReadFilialDto(br.com.fiap.otmav.domain.filial.Filial filial) {
        this(
                filial.getId(),
                filial.getNome(),
                filial.getEndereco() != null ? new ReadEnderecoDto(filial.getEndereco()) : null
        );
    }
}
