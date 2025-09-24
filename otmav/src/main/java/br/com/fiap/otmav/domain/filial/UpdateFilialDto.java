package br.com.fiap.otmav.domain.filial;

import jakarta.validation.constraints.Size;

public record UpdateFilialDto(
        @Size(max = 150)
        String nome,

        Long enderecoId
) {
}
