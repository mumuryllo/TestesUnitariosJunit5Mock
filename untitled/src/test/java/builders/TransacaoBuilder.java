package builders;

import domain.Conta;
import domain.Transacao;

import java.time.LocalDate;

public class TransacaoBuilder {

    private Transacao transacao;
    private TransacaoBuilder (){

    }

    public static TransacaoBuilder umaTransicao(){
       TransacaoBuilder builder = new TransacaoBuilder();
       incializarDadosPadrao(builder);
       return builder;
    }

    public static  void incializarDadosPadrao(TransacaoBuilder builder){
        builder.transacao = new Transacao();
        Transacao elemento = builder.transacao;
        elemento.setId(1L);
        elemento.setDescricao("Transação Válida");
        elemento.setValue(10.0);
        elemento.setConta(ContaBuilder.umaConta().agora());
        elemento.setData(LocalDate.now());
        elemento.setStatus(false);
    }

    public TransacaoBuilder comId(Long param){
        transacao.setId(param);
        return this;
    }

    public TransacaoBuilder comDescricao(String param){
        transacao.setDescricao(param);
        return this;
    }

    public TransacaoBuilder comValor(Double param){
        transacao.setValue(param);
        return this;
    }

    public TransacaoBuilder comConta(Conta param){
        transacao.setConta(param);
        return this;
    }

    public TransacaoBuilder comData(LocalDate param){
        transacao.setData(param);
        return this;
    }

    public TransacaoBuilder comStatus(Boolean param){
        transacao.setStatus(param);
        return this;
    }

    public Transacao agora(){
        return  transacao;
    }

}
