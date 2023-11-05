package service;

import domain.Conta;
import exceptions.ValidationException;
import repositories.ContaRepository;
import service.external.ContaEvent;
import service.external.ContaEvent.EventType;

import java.time.LocalDateTime;
import java.util.List;

public class ContaService {

    private ContaRepository repository;
    private ContaEvent event;
    public ContaService(ContaRepository repository,ContaEvent event) {
        this.repository = repository;
        this.event = event;
    }

    public Conta salvar(Conta conta){
        List<Conta> contas = repository.obterContasPorUsuario(conta.usuario().Id());
        contas.stream().forEach(contaExistente ->{
            if (conta.nome().equals(contaExistente.nome()))
                throw new ValidationException("Usuário já possui uma conta com esse nome");
        });

        Conta contaPersistida = repository.salvar(
                new Conta(conta.id(), conta.nome() + LocalDateTime.now(), conta.usuario()));
        try {
            event.dispatch(contaPersistida, EventType.CREATED);
        } catch (Exception e) {
            repository.delete(contaPersistida);
            throw new RuntimeException("Falha na criação da conta tente novamente");
        }

        return contaPersistida;
    }

}
