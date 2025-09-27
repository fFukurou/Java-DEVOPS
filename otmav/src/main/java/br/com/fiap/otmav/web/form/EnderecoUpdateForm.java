package br.com.fiap.otmav.web.form;

import br.com.fiap.otmav.domain.endereco.UpdateEnderecoDto;
import jakarta.validation.constraints.Size;

public class EnderecoUpdateForm {

    private Integer numero;

    @Size(max = 30)
    private String estado;

    @Size(max = 50)
    private String codigoPais;

    @Size(max = 50)
    private String codigoPostal;

    @Size(max = 150)
    private String complemento;

    @Size(max = 100)
    private String rua;

    public EnderecoUpdateForm() {}

    // constructor used in edit form
    public EnderecoUpdateForm(Integer numero, String estado, String codigoPais, String codigoPostal, String complemento, String rua) {
        this.numero = numero;
        this.estado = estado;
        this.codigoPais = codigoPais;
        this.codigoPostal = codigoPostal;
        this.complemento = complemento;
        this.rua = rua;
    }

    public UpdateEnderecoDto toUpdateDto() {
        return new UpdateEnderecoDto(numero, estado, codigoPais, codigoPostal, complemento, rua);
    }

    // getters / setters
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCodigoPais() { return codigoPais; }
    public void setCodigoPais(String codigoPais) { this.codigoPais = codigoPais; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }
}
