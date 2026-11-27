package repository;

import model.Perfil;
import model.Usuario;

import java.util.List;
import java.util.Optional;

// a chave do usuário é o login, então n tem como ter dois logins iguais
public class UsuarioRepository extends RepositoryGeral<Usuario, String> {

    @Override
    protected String getChave(Usuario u) {
        return u.getLogin();
    }

    public Optional<Usuario> buscarPorLogin(String login) { //optional pq tem o risco de retornar nada
        return buscarComChave(login);
    }

    public boolean existePorLogin(String login) {
        return buscarComChave(login).isPresent();
    }

    public List<Usuario> buscarPorPerfil(Perfil perfil) {
        return listarTodos().stream().filter(u -> u.getPerfil() == perfil).toList();
    }
}
