package br.com.fiap.otmav.domain.funcionario;

import jakarta.validation.constraints.Size;

public record UpdateFuncionarioDto(
        @Size(max = 100)
        String cargo,

        Long dadosId,

        Long filialId
) {}
