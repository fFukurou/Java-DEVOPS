package br.com.fiap.otmav.domain.moto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateMotoDto(
        @Size(max = 7) String placa,
        @Size(max = 17) String chassi,
        @NotBlank @Size(max = 8) String condicao,
        @NotNull String localizacao,
        Long motoristaId,
        Long modeloId,
        Long setorId,
        Long situacaoId
) {}
