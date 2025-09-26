package br.com.fiap.otmav.web.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FuncionarioCreateForm {
    @NotBlank @Size(max = 100)
    private String cargo;

    // Dados fields
    @NotBlank @Size(min = 11, max = 11)
    private String cpf;

    @Size(max = 13)
    private String telefone;

    @Email @Size(max = 255)
    private String email;

    @Size(max = 200)
    private String senha;

    @NotBlank @Size(max = 150)
    private String nome;

    private Long filialId;

    // getters / setters

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getFilialId() {
        return filialId;
    }

    public void setFilialId(Long filialId) {
        this.filialId = filialId;
    }
}
