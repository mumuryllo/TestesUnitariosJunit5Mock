package service;

import domain.Usuario;
import exceptions.ValidationException;
import repositories.UsuarioRepository;

import java.util.Optional;

public class UsuarioService {

    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario salvar(Usuario usuario){
        repository.getUserByEmail(usuario.Email()).ifPresent(user ->{
            throw  new ValidationException(String.format("Usuário já cadastrado!",usuario.Email()));
        });
        return repository.salvar(usuario);
    }

    public Optional<Usuario> getUserByEmail(String email){
        return  repository.getUserByEmail(email);
    }

}
