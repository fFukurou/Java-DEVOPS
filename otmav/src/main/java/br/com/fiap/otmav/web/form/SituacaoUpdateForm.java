package br.com.fiap.otmav.web.form;

import br.com.fiap.otmav.domain.situacao.UpdateSituacaoDto;
import br.com.fiap.otmav.domain.situacao.SituacaoStatus;
import jakarta.validation.constraints.Size;

public class SituacaoUpdateForm {

    private String nome;

    @Size(max = 250)
    private String descricao;

    private SituacaoStatus status;

    public SituacaoUpdateForm() {}

    public SituacaoUpdateForm(String nome, String descricao, SituacaoStatus status) {
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
    }

    public UpdateSituacaoDto toUpdateDto() {
        return new UpdateSituacaoDto(nome, descricao, status);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public SituacaoStatus getStatus() { return status; }
    public void setStatus(SituacaoStatus status) { this.status = status; }
}
