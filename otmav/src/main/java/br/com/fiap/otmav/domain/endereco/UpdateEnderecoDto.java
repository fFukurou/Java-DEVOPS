package br.com.fiap.otmav.domain.endereco;

import jakarta.validation.constraints.Size;

public record UpdateEnderecoDto(
        Integer numero,
        @Size(max = 30) String estado,
        @Size(max = 50) String codigoPais,
        @Size(max = 50) String codigoPostal,
        @Size(max = 150) String complemento,
        @Size(max = 100) String rua
) {}
