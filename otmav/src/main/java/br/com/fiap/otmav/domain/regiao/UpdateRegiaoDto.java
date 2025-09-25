package br.com.fiap.otmav.domain.regiao;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateRegiaoDto(
        @Size(max = 4000) String localizacao,
        @Positive(message = "Area deve ser positiva.") Double area
) {}
