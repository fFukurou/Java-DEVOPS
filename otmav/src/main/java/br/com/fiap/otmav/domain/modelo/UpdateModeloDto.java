package br.com.fiap.otmav.domain.modelo;

import jakarta.validation.constraints.Size;

public record UpdateModeloDto(
        @Size(max = 25) String nomeModelo,
        @Size(max = 50) String frenagem,
        @Size(max = 100) String sisPartida,
        Integer tanque,
        @Size(max = 50) String tipoCombustivel,
        Integer consumo
) {}
