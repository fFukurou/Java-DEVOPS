package br.com.fiap.otmav.domain.setor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateSetorDto(
        @NotNull Integer qtdMoto,
        @NotNull Integer capacidade,
        @Size(max = 250) String nomeSetor,
        @Size(max = 250) String descricao,
        @NotNull String cor,
        Long patioId,
        Long regiaoId
) {}
