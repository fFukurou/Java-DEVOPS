package br.com.fiap.otmav.domain.situacao;

import jakarta.validation.constraints.Size;

public record UpdateSituacaoDto(
        String nome,
        @Size(max = 250) String descricao,
        SituacaoStatus status
) {}
