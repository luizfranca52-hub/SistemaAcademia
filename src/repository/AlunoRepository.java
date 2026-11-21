package repository;

import model.Aluno;
import java.util.List;
import java.util.Optional;

public class AlunoRepository extends RepositoryGeral<Aluno, Integer> {

    @Override
    protected Integer getChave(Aluno a) {
        return a.getId();
    }

    public Optional<Aluno> buscarPorId(Integer id) { //optional pq tem o risco de retornar nada
        return buscarComChave(id);
    }

    public List<Aluno> buscarPorNome(String nome) {
        return listarTodos().stream().filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase())).toList();
    }

    public boolean existePorId(Integer id) {
        return buscarComChave(id).isPresent();
    }

    public Optional<Aluno> autenticar(String login, String senha) {
        return listarTodos().stream().filter(a -> a.autenticar(login, senha)).findFirst();
    }
}