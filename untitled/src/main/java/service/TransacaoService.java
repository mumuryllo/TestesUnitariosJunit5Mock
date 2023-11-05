package service;

import domain.Transacao;
import exceptions.ValidationException;
import repositories.TransacaoDao;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransacaoService {

    private TransacaoDao dao;


    public Transacao salvar (Transacao transacao){
        if (getTime().getHour() >19)
            throw new RuntimeException("Tente novamente amanhã");

             validarCamposObrigatórios(transacao);

             return dao.salvar(transacao);
    }

    private void validarCamposObrigatórios(Transacao transacao){
        if (transacao.getDescricao() == null) throw new ValidationException("Descrição inexistente");
        if (transacao.getValue() == null) throw new ValidationException("Valor inexistente");
        if (transacao.getData() == null) throw new ValidationException("Data inexistente");
        if (transacao.getConta() == null) throw new ValidationException("Conta inexistente");
        if (transacao.getStatus() == null) transacao.setStatus(false);
    }

    protected LocalDateTime getTime(){
        return LocalDateTime.now();
    }

}
