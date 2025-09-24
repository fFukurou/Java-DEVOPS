package br.com.fiap.otmav.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateEnderecoDto(
        @NotNull Integer numero,
        @NotBlank @Size(max = 30) String estado,
        @NotBlank @Size(max = 50) String codigoPais,
        @NotBlank @Size(max = 50) String codigoPostal,
        @Size(max = 150) String complemento,
        @NotBlank @Size(max = 100) String rua
) {}
