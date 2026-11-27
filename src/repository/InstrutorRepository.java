package repository;

import model.Aluno;
import model.Instrutor;

import java.util.List;
import java.util.Optional;

public class InstrutorRepository extends RepositoryGeral<Instrutor, Integer>{
    @Override
    protected Integer getChave(Instrutor i) {
        return i.getId();
    }

    public Optional<Instrutor> buscarPorId(Integer id) { //optional pq tem o risco de retornar nada
        return buscarComChave(id);
    }

    public List<Instrutor> buscarPorNome(String nome) {
        return listarTodos().stream().filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase())).toList();
    }

    public boolean existePorId(Integer id) {
        return buscarComChave(id).isPresent();
    }
}
