package br.com.fiap.otmav.domain.dados;

public record ReadDadosDto(
        Long id,
        String cpf,
        String telefone,
        String email,
        String nome
) {
    public ReadDadosDto(Dados d) {
        this(d.getId(), d.getCpf(), d.getTelefone(), d.getEmail(), d.getNome());
    }
}
