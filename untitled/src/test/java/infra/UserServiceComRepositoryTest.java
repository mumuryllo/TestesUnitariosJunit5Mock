package infra;

import builders.UsuarioBuilder;
import domain.Usuario;
import exceptions.ValidationException;
import org.junit.jupiter.api.*;
import service.UsuarioService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceComRepositoryTest {

    private static UsuarioService service = new UsuarioService(new UserRepositoryMemory());


    @Test
    @Order(1)
    public void deveSalvarUsuarioValido(){
        Usuario user = service.salvar(UsuarioBuilder.umUsuario().comId(null).agora());
        Assertions.assertNotNull(user.Id());
//        Assertions.assertEquals(2L,user.Id());
    }


    @Test
    @Order(2)
    public void deveRejeitarUsuarioExistente(){
       ValidationException ex = Assertions.assertThrows(ValidationException.class,()->
               service.salvar(UsuarioBuilder.umUsuario().comId(null).agora()));
               Assertions.assertEquals("Usuário já cadastrado!",ex.getMessage());
    }


}
