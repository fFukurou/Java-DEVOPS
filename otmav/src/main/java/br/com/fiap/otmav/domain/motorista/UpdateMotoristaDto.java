package br.com.fiap.otmav.domain.motorista;

public record UpdateMotoristaDto(
        MotoristaPlano plano,
        Long dadosId
) {}
