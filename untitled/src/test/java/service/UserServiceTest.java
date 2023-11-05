package service;
import domain.Usuario;
import exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import repositories.UsuarioRepository;

import java.util.Optional;

import static builders.UsuarioBuilder.umUsuario;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    // Criar um mock do que eu quero
    @Mock
    private UsuarioRepository repository;

    // Injeta todos os mocks pra essa classe que eu estou testando
    @InjectMocks
    private UsuarioService service;


    // Com o extend eu não preciso mais desse BeforeEach

//    @BeforeEach
//    public void setup(){
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    public void deveRetornarEmptyQuandoUsuarioInesxistente(){

        when(repository.getUserByEmail("mail@mail.com")).thenReturn(Optional.empty());

        Optional<Usuario> user = service.getUserByEmail("mail@mail.com");
        Assertions.assertTrue(user.isEmpty());
    }


    @Test
    public void deveRetornarUsuarioporEmail(){

        when(repository.getUserByEmail("mail@mail.com"))
                .thenReturn(Optional.of(umUsuario().agora()));

        Optional<Usuario> user = service.getUserByEmail("mail@mail.com");
        Assertions.assertTrue(user.isPresent());

        // Depois de passar pela interação eu posso verificar se ela ocorreu
        verify(repository, times(1)).getUserByEmail("mail@mail.com");
        verify(repository, never()).getUserByEmail("mumu@mail.com");
    }

    @Test
    public void deveSalvarUsuarioComSucesso(){

        Usuario usuarioSalvo = umUsuario().comId(null).agora();

        // Duas interações do metodo
//        when(repository.getUserByEmail(usuarioSalvo.Email()))
//                .thenReturn(Optional.empty());
        when(repository.salvar(usuarioSalvo))
                .thenReturn(umUsuario().agora());

        Usuario saveUser = service.salvar(usuarioSalvo);
        Assertions.assertNotNull(saveUser.Id());

        verify(repository).getUserByEmail(usuarioSalvo.Email());
//        verify(repository).salvar(usuarioSalvo);

    }

    @Test
    public  void deveRejeitarUsuarioExistente(){
        Usuario userSave = umUsuario().comId(null).agora();
        when(repository.getUserByEmail(userSave.Email()))
                .thenReturn(Optional.of(umUsuario().agora()))
        ;

        ValidationException ex = Assertions.assertThrows(ValidationException.class,() ->
         service.salvar(userSave));
        Assertions.assertTrue(ex.getMessage().endsWith("já cadastrado!"));

        verify(repository, never()).salvar(userSave);

    }



}
