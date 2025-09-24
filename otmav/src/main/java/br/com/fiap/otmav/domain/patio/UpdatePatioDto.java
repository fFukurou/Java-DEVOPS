package br.com.fiap.otmav.domain.patio;

public record UpdatePatioDto(
        Integer totalMotos,
        Integer capacidadeMoto,
        Long filialId,
        Long regiaoId
) {}
