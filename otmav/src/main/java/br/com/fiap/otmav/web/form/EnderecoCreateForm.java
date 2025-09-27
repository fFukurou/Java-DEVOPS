package br.com.fiap.otmav.web.form;

import br.com.fiap.otmav.domain.endereco.CreateEnderecoDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EnderecoCreateForm {

    @NotNull
    private Integer numero;

    @NotBlank @Size(max = 30)
    private String estado;

    @NotBlank @Size(max = 50)
    private String codigoPais;

    @NotBlank @Size(max = 50)
    private String codigoPostal;

    @Size(max = 150)
    private String complemento;

    @NotBlank @Size(max = 100)
    private String rua;

    public EnderecoCreateForm() {}

    public CreateEnderecoDto toCreateDto() {
        return new CreateEnderecoDto(numero, estado, codigoPais, codigoPostal, complemento, rua);
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
