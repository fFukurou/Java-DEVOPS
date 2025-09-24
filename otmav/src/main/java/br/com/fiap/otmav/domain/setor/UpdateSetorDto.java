package br.com.fiap.otmav.domain.setor;

public record UpdateSetorDto(
        Integer qtdMoto,
        Integer capacidade,
        String nomeSetor,
        String descricao,
        String cor,
        Long patioId,
        Long regiaoId
) {}
