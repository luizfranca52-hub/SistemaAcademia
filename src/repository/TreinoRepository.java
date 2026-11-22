package repository;

import model.Treino;

import java.util.List;
import java.util.Optional;

public class TreinoRepository extends RepositoryGeral<Treino, Integer> {

    @Override
    protected Integer getChave(Treino treino) {
        return treino.getId();
    }

    public Optional<Treino> buscarPorId(Integer id) {
        return buscarComChave(id);
    }

    public List<Treino> buscarPorAluno(Integer idAluno) {
        return listarTodos().stream().filter(t -> t.getAluno().getId() == idAluno).toList();
    }

    public List<Treino> buscarPorInstrutor(Integer idInstrutor) {
        return listarTodos().stream()
                .filter(t -> t.getInstrutor().getId() == idInstrutor)
                .toList();
    }

    public boolean existePorId(Integer id) {
        return buscarComChave(id).isPresent();
    }
}