package br.com.fiap.otmav.web.form;

import br.com.fiap.otmav.domain.modelo.CreateModeloDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ModeloCreateForm {

    @NotBlank @Size(max = 25)
    private String nomeModelo;

    @Size(max = 50)
    private String frenagem;

    @Size(max = 100)
    private String sisPartida;

    @NotNull
    private Integer tanque;

    @NotBlank @Size(max = 50)
    private String tipoCombustivel;

    @NotNull
    private Integer consumo;

    public ModeloCreateForm() {}

    public CreateModeloDto toCreateDto() {
        return new CreateModeloDto(nomeModelo, frenagem, sisPartida, tanque, tipoCombustivel, consumo);
    }

    // getters / setters
    public String getNomeModelo() { return nomeModelo; }
    public void setNomeModelo(String nomeModelo) { this.nomeModelo = nomeModelo; }

    public String getFrenagem() { return frenagem; }
    public void setFrenagem(String frenagem) { this.frenagem = frenagem; }

    public String getSisPartida() { return sisPartida; }
    public void setSisPartida(String sisPartida) { this.sisPartida = sisPartida; }

    public Integer getTanque() { return tanque; }
    public void setTanque(Integer tanque) { this.tanque = tanque; }

    public String getTipoCombustivel() { return tipoCombustivel; }
    public void setTipoCombustivel(String tipoCombustivel) { this.tipoCombustivel = tipoCombustivel; }

    public Integer getConsumo() { return consumo; }
    public void setConsumo(Integer consumo) { this.consumo = consumo; }
}
