package br.com.fiap.otmav.web.form;

import br.com.fiap.otmav.domain.situacao.CreateSituacaoDto;
import br.com.fiap.otmav.domain.situacao.SituacaoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SituacaoCreateForm {

    @NotBlank
    @Size(max = 250)
    private String nome;

    @Size(max = 250)
    private String descricao;

    @NotNull
    private SituacaoStatus status;

    public SituacaoCreateForm() {}

    public CreateSituacaoDto toCreateDto() {
        return new CreateSituacaoDto(nome, descricao, status);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public SituacaoStatus getStatus() { return status; }
    public void setStatus(SituacaoStatus status) { this.status = status; }
}
