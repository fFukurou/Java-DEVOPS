package br.com.fiap.otmav.domain.moto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateMotoDto(
        @Size(max = 100) String placa,
        @Size(max = 17) String chassi,
        @NotBlank String condicao,
        @NotNull String localizacao,
        Long motoristaId,
        Long modeloId,
        Long setorId,
        Long situacaoId
) {}
