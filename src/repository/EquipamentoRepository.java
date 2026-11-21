package repository;

import model.Aluno;
import model.Equipamento;

import java.util.List;
import java.util.Optional;

public class EquipamentoRepository extends RepositoryGeral<Equipamento, Integer> {
    @Override
    protected Integer getChave(Equipamento e) {
        return e.getId();
    }

    public Optional<Equipamento> buscarPorId(Integer id) { //optional pq tem o risco de retornar nada
        return buscarComChave(id);
    }

    public List<Equipamento> buscarPorNome(String nome) {
        return listarTodos().stream().filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase())).toList();
    }

    public boolean existePorId(Integer id) {
        return buscarComChave(id).isPresent();
    }

}
