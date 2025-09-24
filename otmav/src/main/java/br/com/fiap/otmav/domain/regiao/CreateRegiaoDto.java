package br.com.fiap.otmav.domain.regiao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateRegiaoDto(
        @Size(max = 4000) String localizacaoWkt,
        @NotNull Double area
) {}
