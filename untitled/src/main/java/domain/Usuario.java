package domain;

import exceptions.ValidationException;

import java.util.Objects;

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;



    public Usuario(Long id, String nome, String email, String senha) {
        if (nome == null)  throw new ValidationException("Nome é obrigatório");
        if (email == null)  throw new ValidationException("Email é obrigatório");
        if (senha == null)  throw new ValidationException("Senha é obrigatória");

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Long Id() {
        return id;
    }

    public String Nome() {
        return nome;
    }

    public String Email() {
        return email;
    }

    public String Senha() {
        return senha;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nome, usuario.nome) && Objects.equals(email, usuario.email) && Objects.equals(senha, usuario.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, email, senha);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
