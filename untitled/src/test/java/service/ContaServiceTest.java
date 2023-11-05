package service;

import builders.ContaBuilder;
import domain.Conta;
import exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repositories.ContaRepository;
import service.external.ContaEvent;
import service.external.ContaEvent.EventType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static builders.ContaBuilder.umaConta;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

    @Mock
    private ContaRepository repository;

    @Mock
    private ContaEvent event;

    @InjectMocks
    private ContaService service;

    // Captor serve pra pegar os valores quando não temos controle de algo
    @Captor
    private ArgumentCaptor<Conta> contaCaptor;

    @Test
    public void deveSalvarContaComSucesso() throws Exception {

        //Usamos any, quando não nos importamos com os dados que vão chegar

        Conta contaSave = umaConta().comId(null).agora();
        when(repository.salvar(any(Conta.class))).thenReturn(umaConta().agora());

        // Mockando métodos void
        // donothing - não faça nada
        doNothing().when(event).dispatch(umaConta().agora(), EventType.CREATED);


        Conta savedConta = service.salvar(contaSave);
        Assertions.assertNotNull(savedConta.id());

        verify(repository).salvar(contaCaptor.capture());
        Assertions.assertNull(contaCaptor.getValue().id());
        Assertions.assertTrue(contaCaptor.getValue().nome().startsWith("Conta"));


    }

    @Test
    public void deveRejeitarContaRepetida(){

        Conta contaSave = umaConta().comId(null).agora();
        when(repository.obterContasPorUsuario(contaSave.usuario().Id()))
                .thenReturn(Arrays.asList(umaConta().agora()));
//        when(repository.salvar(contaSave)).thenReturn(umaConta().agora());

        String mensagem = Assertions.assertThrows(ValidationException.class,()->
                service.salvar(contaSave)).getMessage();
        Assertions.assertEquals("Usuário já possui uma conta com esse nome",mensagem);

    }

    @Test
    public  void naoDeveManterContaSemEvento() throws Exception {
        Conta contaSave = umaConta().comId(null).agora();
        Conta contaSalva = umaConta().agora();

        when(repository.salvar(any(Conta.class))).thenReturn(contaSalva);
        doThrow(new Exception("Houve falha")).when(event).dispatch(contaSalva,EventType.CREATED);

        String mensagem = Assertions.assertThrows(Exception.class,()->
                service.salvar(contaSave)).getMessage();
        Assertions.assertEquals("Falha na criação da conta tente novamente",mensagem);
        verify(repository).delete(contaSalva);
    }

    @Test
    public void deveSalvarContaMesmoJaExistindoOutras(){

        Conta contaSave = umaConta().comId(null).agora();
        when(repository.obterContasPorUsuario(contaSave.usuario().Id()))
                .thenReturn(Arrays.asList(umaConta().comNome("Outra Conta").agora()));
         when(repository.salvar(any(Conta.class))).thenReturn(umaConta().agora());


        Conta savedConta = service.salvar(contaSave);
        Assertions.assertNotNull(savedConta.id());
    }


}
