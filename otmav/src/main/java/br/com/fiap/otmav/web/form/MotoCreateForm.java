package br.com.fiap.otmav.web.form;

import br.com.fiap.otmav.domain.moto.CreateMotoDto;
import br.com.fiap.otmav.domain.moto.Moto; // optional

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MotoCreateForm {

    @Size(max = 7)
    private String placa;

    @Size(max = 17)
    private String chassi;

    @NotBlank
    private String condicao;

    @NotNull
    private String localizacao;

    private Long motoristaId;
    private Long modeloId;
    private Long setorId;
    private Long situacaoId;

    public MotoCreateForm() {}

    public CreateMotoDto toCreateDto() {
        return new CreateMotoDto(placa, chassi, condicao, localizacao, motoristaId, modeloId, setorId, situacaoId);
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Long getMotoristaId() {
        return motoristaId;
    }

    public void setMotoristaId(Long motoristaId) {
        this.motoristaId = motoristaId;
    }

    public Long getModeloId() {
        return modeloId;
    }

    public void setModeloId(Long modeloId) {
        this.modeloId = modeloId;
    }

    public Long getSetorId() {
        return setorId;
    }

    public void setSetorId(Long setorId) {
        this.setorId = setorId;
    }

    public Long getSituacaoId() {
        return situacaoId;
    }

    public void setSituacaoId(Long situacaoId) {
        this.situacaoId = situacaoId;
    }
}
