package repository;

import model.Aluno;
import java.util.List;
import java.util.Optional;

public class AlunoRepository extends RepositoryGeral<Aluno, String> {

    @Override
    protected String getChave(Aluno a) {
        return a.getCpf();
    }

    public Optional<Aluno> buscarPorCpf(String cpf) { //optional pq tem o risco de retornar nada
        return buscarComChave(cpf);
    }

    public List<Aluno> buscarPorNome(String nome) {
        return listarTodos().stream().filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase())).toList();
    }

    public boolean existePorCpf(String cpf) {
        return buscarComChave(cpf).isPresent();
    }

    public Optional<Aluno> autenticar(String login, String senha) {
        return listarTodos().stream().filter(a -> a.autenticar(login, senha)).findFirst();
    }
}