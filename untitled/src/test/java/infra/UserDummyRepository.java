package infra;

import builders.UsuarioBuilder;
import domain.Usuario;
import repositories.UsuarioRepository;

import java.util.Optional;

import static builders.UsuarioBuilder.umUsuario;

public class UserDummyRepository implements UsuarioRepository {
    @Override
    public Usuario salvar(Usuario usuario) {
        return umUsuario()
                .comNome(usuario.Nome())
                .comEmail(usuario.Email())
                .comSenha(usuario.Senha()).
                agora();
    }

    @Override
    public Optional<Usuario> getUserByEmail(String email) {
        if ("mu@gmail.com".equals(email))
        return Optional.of(umUsuario().comEmail(email).agora());
        return  Optional.empty();
    }
}
