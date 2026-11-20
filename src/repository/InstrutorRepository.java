package repository;

import model.Aluno;
import model.Instrutor;

import java.util.List;
import java.util.Optional;

public class InstrutorRepository extends RepositoryGeral<Instrutor, String>{
    @Override
    protected String getChave(Instrutor i) {
        return i.getCref();
    }

    public Optional<Instrutor> buscarPorCref(String cref) { //optional pq tem o risco de retornar nada
        return buscarComChave(cref);
    }

    public List<Instrutor> buscarPorNome(String nome) {
        return listarTodos().stream().filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase())).toList();
    }

    public boolean existePorCref(String cref) {
        return buscarComChave(cref).isPresent();
    }

    public Optional<Instrutor> autenticar(String login, String senha) {
        return listarTodos().stream().filter(a -> a.autenticar(login, senha)).findFirst();
    }
}
