package br.com.fiap.otmav.domain.motorista;

import jakarta.validation.constraints.NotNull;

public record CreateMotoristaDto(
        @NotNull MotoristaPlano plano,
        @NotNull Long dadosId
) {}
