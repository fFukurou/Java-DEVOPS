package br.com.fiap.otmav.domain.funcionario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateFuncionarioDto(
        @NotBlank
        @Size(max = 100)
        String cargo,

        @NotNull(message = "dadosId é obrigatório.")
        Long dadosId,

        Long filialId
) {}
