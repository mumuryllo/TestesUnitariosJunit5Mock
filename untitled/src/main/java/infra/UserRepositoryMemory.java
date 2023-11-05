package infra;

import domain.Usuario;
import repositories.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryMemory implements UsuarioRepository {

    private List<Usuario> users;
    private Long currentId;

    public UserRepositoryMemory(){
        currentId = 0L;
        users = new ArrayList<>();
        salvar(new Usuario(null,"User #1","user1@mail.com","123456"));
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        Usuario newUser = new Usuario(nextId(), usuario.Nome(), usuario.Email(), usuario.Senha());
        users.add(newUser);
        return newUser;
    }

    @Override
    public Optional<Usuario> getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.Email().equalsIgnoreCase(email))
                .findFirst();
    }

    public void printUsers(){
        System.out.println(users);
    }

    private Long nextId(){
        return ++currentId;
    }



    public static void main(String[] args) {
        UserRepositoryMemory repo = new UserRepositoryMemory();
        repo.salvar(new Usuario(null,"User #2","user@gmail.com","123456"));
        repo.printUsers();
    }

}
