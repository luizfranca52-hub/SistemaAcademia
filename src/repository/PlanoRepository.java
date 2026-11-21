package repository;

import model.Administrativo;
import model.Plano;

import java.util.List;
import java.util.Optional;

public class PlanoRepository extends RepositoryGeral<Plano, Integer>{
    @Override
    protected Integer getChave(Plano p) {
        return p.getId();
    }

    public Optional<Plano> buscarPorId(Integer id) { //optional pq tem o risco de retornar nada
        return buscarComChave(id);
    }

    public List<Plano> buscarPorNome(String nome) {
        return listarTodos().stream().filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase())).toList();
    }

    public boolean existePorId(Integer id) {
        return buscarComChave(id).isPresent();
    }

}
