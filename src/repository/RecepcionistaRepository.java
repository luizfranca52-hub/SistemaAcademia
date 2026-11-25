package repository;

import model.Recepcionista;

import java.util.List;
import java.util.Optional;

public class RecepcionistaRepository extends RepositoryGeral<Recepcionista, Integer> {
    @Override
    protected Integer getChave(Recepcionista r) {
        return r.getId();
    }

    public Optional<Recepcionista> buscarPorId(Integer id) { //optional pq tem o risco de retornar nada
        return buscarComChave(id);
    }

    public List<Recepcionista> buscarPorNome(String nome) {
        return listarTodos().stream().filter(r -> r.getNome().toLowerCase().contains(nome.toLowerCase())).toList();
    }

    public boolean existePorId(Integer id) {
        return buscarComChave(id).isPresent();
    }

    public Optional<Recepcionista> autenticar(String login, String senha) {
        return listarTodos().stream().filter(r -> r.autenticar(login, senha)).findFirst();
    }
}
