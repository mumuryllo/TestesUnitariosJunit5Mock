package domain;

import exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static builders.UsuarioBuilder.umUsuario;
import static org.junit.jupiter.api.Assertions.assertEquals;
@DisplayName("Dominio:Usuário")
public class UsuarioTest {

    private  Usuario usuario;

    @Test
    @DisplayName("Testando se um usuário é válido")
    void deveCriarUsuarioValido(){
        usuario = umUsuario().agora();
        assertEquals(1L,usuario.Id());
        assertEquals("Muryllo Soares",usuario.Nome());
        assertEquals("mu@gmail.com",usuario.Email());
        assertEquals("123456",usuario.Senha());
    }

    @Test
    void deveRejeitarUsuarioSemNome(){
        ValidationException ex = Assertions.assertThrows(ValidationException.class, ()->
                umUsuario().comNome(null).agora()
                );
        assertEquals("Nome é obrigatório",ex.getMessage());
    }


    @Test
    void deveRejeitarUsuarioSemEmail(){
        ValidationException ex = Assertions.assertThrows(ValidationException.class, ()->
                umUsuario().comEmail(null).agora()
        );
        assertEquals("Email é obrigatório",ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,NULL, mu@gmail.com, 123456, Nome é obrigatório",
            "1, Muryllo Soares, NULL, 123456, Email é obrigatório",
            "1,Muryllo Soares, mu@gmail.com, NULL, Senha é obrigatória",
    },nullValues = "NULL")
    void deveValidarCamposObrigatorios(Long id,String nome,String email,String senha,String mensagem){
        ValidationException ex = Assertions.assertThrows(ValidationException.class, ()->
                umUsuario().comId(id).comNome(nome).comEmail(email).comSenha(senha).agora()
        );
        assertEquals(mensagem,ex.getMessage());
    }


    @Test
    void deveRejeitarUsuarioSemSenha(){
        ValidationException ex = Assertions.assertThrows(ValidationException.class, ()->
                umUsuario().comSenha(null).agora()
        );
        assertEquals("Senha é obrigatória",ex.getMessage());
    }
}
