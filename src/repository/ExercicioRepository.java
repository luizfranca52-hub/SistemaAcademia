package repository;

import model.Aluno;
import model.Exercicio;

import java.util.List;
import java.util.Optional;

public class ExercicioRepository extends RepositoryGeral<Exercicio, Integer>{
    @Override
    protected Integer getChave(Exercicio e) {
        return e.getId();
    }

    public Optional<Exercicio> buscarPorId(Integer id) { //optional pq tem o risco de retornar nada
        return buscarComChave(id);
    }

    public List<Exercicio> buscarPorNome(String nome) {
        return listarTodos().stream().filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase())).toList();
    }

    public boolean existePorId(Integer id) {
        return buscarComChave(id).isPresent();

    }
}
