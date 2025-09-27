package br.com.fiap.otmav.web.form;

import br.com.fiap.otmav.domain.modelo.UpdateModeloDto;
import jakarta.validation.constraints.Size;

public class ModeloUpdateForm {

    @Size(max = 25)
    private String nomeModelo;

    @Size(max = 50)
    private String frenagem;

    @Size(max = 100)
    private String sisPartida;

    private Integer tanque;

    @Size(max = 50)
    private String tipoCombustivel;

    private Integer consumo;

    public ModeloUpdateForm() {}

    public ModeloUpdateForm(String nomeModelo, String frenagem, String sisPartida, Integer tanque, String tipoCombustivel, Integer consumo) {
        this.nomeModelo = nomeModelo;
        this.frenagem = frenagem;
        this.sisPartida = sisPartida;
        this.tanque = tanque;
        this.tipoCombustivel = tipoCombustivel;
        this.consumo = consumo;
    }

    public UpdateModeloDto toUpdateDto() {
        return new UpdateModeloDto(nomeModelo, frenagem, sisPartida, tanque, tipoCombustivel, consumo);
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
