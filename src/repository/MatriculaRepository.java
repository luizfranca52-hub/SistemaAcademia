package repository;

import model.Matricula;
import model.StatusMatricula;

import java.util.List;
import java.util.Optional;

public class MatriculaRepository extends RepositoryGeral<Matricula, Integer>{
    @Override
    protected Integer getChave(Matricula m) {
        return m.getId();
    }

    public Optional<Matricula> buscarPorId(Integer id) {
        return buscarComChave(id);
    }

    public boolean existePorId(Integer id) {
        return buscarComChave(id).isPresent();
    }

    public List<Matricula> buscarPorAluno(Integer idAluno) {
        return listarTodos().stream()
                .filter(m -> m.getAluno().getId() == idAluno)
                .toList();
    }

    public List<Matricula> buscarPorPlano(Integer idPlano) {
        return listarTodos().stream()
                .filter(m -> m.getPlano().getId() == idPlano)
                .toList();
    }

    public List<Matricula> buscarPorStatus(StatusMatricula status) {
        return listarTodos().stream()
                .filter(m -> m.getStatus() == status)
                .toList();
    }

    public List<Matricula> listarAtivas() {
        return listarTodos().stream()
                .filter(Matricula::estaAtiva)
                .toList();
    }
}
