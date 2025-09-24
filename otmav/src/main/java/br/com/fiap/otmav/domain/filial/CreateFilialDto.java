package br.com.fiap.otmav.domain.filial;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateFilialDto(
        @NotBlank
        @Size(max = 150)
        String nome,

        Long enderecoId
) {
}
