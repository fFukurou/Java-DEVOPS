package br.com.fiap.otmav.domain.regiao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateRegiaoDto(
        @Size(max = 4000) String localizacao,
        @NotNull @Positive(message = "Area deve ser positiva.") Double area
) {}
