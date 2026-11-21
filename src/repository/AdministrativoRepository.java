package repository;

import model.Administrativo;
import model.Aluno;
import java.util.List;
import java.util.Optional;

public class AdministrativoRepository extends RepositoryGeral<Administrativo, Integer> {
    @Override
    protected Integer getChave(Administrativo a) {
        return a.getId();
    }

    public Optional<Administrativo> buscarPorId(Integer id) { //optional pq tem o risco de retornar nada
        return buscarComChave(id);
    }

    public List<Administrativo> buscarPorNome(String nome) {
        return listarTodos().stream().filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase())).toList();
    }

    public boolean existePorId(Integer id) {
        return buscarComChave(id).isPresent();
    }

    public Optional<Administrativo> autenticar(String login, String senha) {
        return listarTodos().stream().filter(a -> a.autenticar(login, senha)).findFirst();
    }
}