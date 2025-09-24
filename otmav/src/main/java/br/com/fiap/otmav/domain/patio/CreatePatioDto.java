package br.com.fiap.otmav.domain.patio;

import jakarta.validation.constraints.NotNull;

public record CreatePatioDto(
        @NotNull Integer totalMotos,
        @NotNull Integer capacidadeMoto,
        Long filialId,
        Long regiaoId
) {}
