package br.com.fiap.otmav.domain.funcionario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Create expects an already existing Dados record (id) to link to the Funcionario.
 * If you prefer to create Dados inside this DTO, we can extend it later.
 */
public record CreateFuncionarioDto(
        @NotBlank
        @Size(max = 100)
        String cargo,

        @NotNull(message = "dadosId is required")
        Long dadosId,

        Long filialId
) {}
