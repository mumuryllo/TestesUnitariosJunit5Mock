package builders;

import domain.Conta;
import domain.Usuario;

public class ContaBuilder {

    private Long id;
    private String nome;
    private Usuario usuario;

    private ContaBuilder(){

    }
    public static ContaBuilder umaConta(){
        ContaBuilder builder = new ContaBuilder();
        incializarDadosPadroes(builder);
        return  builder;
    }

    public static void incializarDadosPadroes(ContaBuilder builder) {
        builder.id = 1L;
        builder.nome = "Conta Válida";
        builder.usuario = UsuarioBuilder.umUsuario().agora();
    }

    public ContaBuilder comId(Long id){
        this.id = id;
        return  this;
    }

    public ContaBuilder comNome(String nome){
        this.nome = nome;
        return  this;
    }

    public ContaBuilder comUsuario(Usuario usuario){
        this.usuario = usuario;
        return  this;
    }

    public Conta agora(){
        return new Conta(id,nome,usuario);
    }

}
