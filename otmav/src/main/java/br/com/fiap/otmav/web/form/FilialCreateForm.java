package br.com.fiap.otmav.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FilialCreateForm {

    @NotBlank
    @Size(max = 150)
    private String nome;

    @NotNull(message = "ID de endereco obrigatorio.")
    private Long enderecoId;

    public FilialCreateForm() {}

    public FilialCreateForm(String nome, Long enderecoId) {
        this.nome = nome;
        this.enderecoId = enderecoId;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Long getEnderecoId() { return enderecoId; }
    public void setEnderecoId(Long enderecoId) { this.enderecoId = enderecoId; }
}
