package service;

import builders.ContaBuilder;
import builders.TransacaoBuilder;
import domain.Conta;
import domain.Transacao;
import exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import repositories.TransacaoDao;
import service.external.ClockService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static builders.ContaBuilder.umaConta;
import static builders.TransacaoBuilder.umaTransicao;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TransacaoServiceTest {
    @Mock
    private TransacaoDao dao;

    @InjectMocks
    @Spy
    private TransacaoService service;

    @BeforeEach
    public void setup(){
//        when(clock.getCurrentTime()).thenReturn(LocalDateTime.of(2023,1,1,4,30,15));
          when(service.getTime()).thenReturn(LocalDateTime.of(2023,1,1,4,30,15));
    }

    // Spy é ótimo pra quando voce não sabe o que testar de fato

    @Test
    public void  deveSalvarumaTransicaoValida(){
        Transacao transacaoParaSalvar = umaTransicao().comId(null).agora();
        when(dao.salvar(transacaoParaSalvar)).thenReturn(umaTransicao().agora());
        when(service.getTime()).thenReturn(LocalDateTime.of(2023,1,1,4,30,15));

        Transacao transacaoSalva = service.salvar(transacaoParaSalvar);
        assertEquals(umaTransicao().agora(),transacaoSalva);

        assertAll("Transacao",
                () -> assertEquals(1L, transacaoSalva.getId()),
                () -> assertEquals("Transação Válida", transacaoSalva.getDescricao()),
                () -> {
                    assertAll("Usuario",
                            () -> assertEquals("Muryllo Soares", transacaoSalva.getConta().usuario().Nome()),
                            () -> assertEquals("123456", transacaoSalva.getConta().usuario().Senha())
                    );
                }

        );
    }


    @ParameterizedTest(name = "{6}")
    @MethodSource(value = "cenariosObrigatorios")
    public void  deveValidarCamposObrigatoriosAoSalvar(Long id,String descricao,Double valor,
                                                       LocalDate data, Conta conta,Boolean status,
                                                       String mensagem
                                                       ){
     String exmessage =   Assertions.assertThrows(ValidationException.class,()->{
            Transacao transacao = umaTransicao().comId(id).comDescricao(descricao).comValor(valor)
                    .comData(data).comStatus(status).comConta(conta).agora();
            service.salvar(transacao);
        }).getMessage();
        assertEquals(mensagem,exmessage);
    }

    static Stream<Arguments> cenariosObrigatorios(){
        return Stream.of(
                Arguments.of(1L,null,10D,LocalDate.now(), umaConta().agora(),true,"Descrição inexistente"),
                Arguments.of(1L,"Descricao",null,LocalDate.now(), umaConta().agora(),true,"Valor inexistente"),
                Arguments.of(1L,"Descricao",10D,null, umaConta().agora(),true,"Data inexistente"),
                Arguments.of(1L,"Descricao",10D,LocalDate.now(), null,true,"Conta inexistente")

                );
    }

    //Testando métodos privados
    @Test
    public void deveRejeitarTransacaoSemValor() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Transacao transacao = umaTransicao().comValor(null).agora();

        Method metodo = TransacaoService.class.getDeclaredMethod("validarCamposObrigatórios", Transacao.class);
        metodo.setAccessible(true);

       Exception ex = Assertions.assertThrows(Exception.class,()->
                metodo.invoke(new TransacaoService(),transacao));
       assertEquals("Valor inexistente",ex.getCause().getMessage());
    }

}
