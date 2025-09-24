package br.com.fiap.otmav.domain.regiao;

import jakarta.validation.constraints.Size;

public record UpdateRegiaoDto(
        @Size(max = 4000) String localizacaoWkt,
        Double area
) {}
