package br.com.fiap.otmav.web.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import br.com.fiap.otmav.domain.setor.CreateSetorDto;

public class SetorCreateForm {

    @NotNull @Min(0)
    private Integer qtdMoto;

    @NotNull @Min(0)
    private Integer capacidade;

    @Size(max = 250)
    private String nomeSetor;

    @Size(max = 250)
    private String descricao;

    @NotNull
    @Size(max = 20)
    private String cor;

    private Long patioId;
    private Long regiaoId;

    public SetorCreateForm() {}

    public CreateSetorDto toCreateDto() {
        return new CreateSetorDto(qtdMoto, capacidade, nomeSetor, descricao, cor, patioId, regiaoId);
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
