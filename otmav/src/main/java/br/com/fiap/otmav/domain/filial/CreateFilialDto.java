package br.com.fiap.otmav.domain.filial;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public record CreateFilialDto(
        @NotBlank
        @Size(max = 150)
        String nome,

        @NotNull(message = "ID de endereco obrigatorio.")
        Long enderecoId
) {
}
