package br.com.fiap.otmav.domain.moto;

public record UpdateMotoDto(
        String placa,
        String chassi,
        String condicao,
        String localizacao,
        Long motoristaId,
        Long modeloId,
        Long setorId,
        Long situacaoId
) {}
