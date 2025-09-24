package br.com.fiap.otmav.domain.modelo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateModeloDto(
        @NotBlank @Size(max = 25) String nomeModelo,
        @Size(max = 50) String frenagem,
        @Size(max = 100) String sisPartida,
        @NotNull Integer tanque,
        @NotBlank @Size(max = 50) String tipoCombustivel,
        @NotNull Integer consumo
) {}
