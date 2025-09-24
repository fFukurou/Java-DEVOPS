package br.com.fiap.otmav.domain.situacao;

public record ReadSituacaoDto(
        Long id,
        String nome,
        String descricao,
        SituacaoStatus status
) {
    public ReadSituacaoDto(Situacao s) {
        this(s.getId(), s.getNome(), s.getDescricao(), s.getStatus());
    }
}
