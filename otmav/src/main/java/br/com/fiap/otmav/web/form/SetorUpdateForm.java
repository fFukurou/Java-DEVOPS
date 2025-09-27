package br.com.fiap.otmav.web.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import br.com.fiap.otmav.domain.setor.UpdateSetorDto;

public class SetorUpdateForm {

    @Min(0)
    private Integer qtdMoto;

    @Min(0)
    private Integer capacidade;

    @Size(max = 250)
    private String nomeSetor;

    @Size(max = 250)
    private String descricao;

    @Size(max = 20)
    private String cor;

    private Long patioId;
    private Long regiaoId;

    public SetorUpdateForm() {}

    public SetorUpdateForm(Integer qtdMoto, Integer capacidade, String nomeSetor, String descricao, String cor, Long patioId, Long regiaoId) {
        this.qtdMoto = qtdMoto;
        this.capacidade = capacidade;
        this.nomeSetor = nomeSetor;
        this.descricao = descricao;
        this.cor = cor;
        this.patioId = patioId;
        this.regiaoId = regiaoId;
    }

    public UpdateSetorDto toUpdateDto() {
        return new UpdateSetorDto(qtdMoto, capacidade, nomeSetor, descricao, cor, patioId, regiaoId);
    }

    // getters / setters
    public Integer getQtdMoto() { return qtdMoto; }
    public void setQtdMoto(Integer qtdMoto) { this.qtdMoto = qtdMoto; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }

    public String getNomeSetor() { return nomeSetor; }
    public void setNomeSetor(String nomeSetor) { this.nomeSetor = nomeSetor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public Long getPatioId() { return patioId; }
    public void setPatioId(Long patioId) { this.patioId = patioId; }

    public Long getRegiaoId() { return regiaoId; }
    public void setRegiaoId(Long regiaoId) { this.regiaoId = regiaoId; }
}
