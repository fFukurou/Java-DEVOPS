package br.com.fiap.otmav.domain.situacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateSituacaoDto(
        @NotBlank @Size(max = 250) String nome,
        @Size(max = 250) String descricao,
        @NotNull SituacaoStatus status
) {}
