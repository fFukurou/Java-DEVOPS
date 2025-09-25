package br.com.fiap.otmav.domain.moto;

import jakarta.validation.constraints.NotNull;

public record TransferSetorDto(
    @NotNull Long setorId
) {}
