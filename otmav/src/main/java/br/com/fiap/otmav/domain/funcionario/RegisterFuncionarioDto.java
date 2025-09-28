package br.com.fiap.otmav.domain.funcionario;

import br.com.fiap.otmav.domain.dados.CreateDadosDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record RegisterFuncionarioDto(
        @NotBlank
        @Size(max = 100)
        String cargo,

        @NotNull
        CreateDadosDto dados,

        Long filialId
) {}
