package repository;

import model.Matricula;
import model.Pagamento;
import model.StatusMatricula;

import java.util.List;
import java.util.Optional;

public class PagamentoRepository extends RepositoryGeral<Pagamento, Integer> {
    @Override
    protected Integer getChave(Pagamento p) {
        return p.getId();
    }

    public Optional<Pagamento> buscarPorId(Integer id) {
        return buscarComChave(id);
    }

    public boolean existePorId(Integer id) {
        return buscarComChave(id).isPresent();
    }

    public List<Pagamento> buscarPorAluno(Integer idAluno) {
        return listarTodos().stream().filter(m -> m.getAluno().getId() == idAluno).toList();
    }

    public List<Pagamento> buscarPorMatricula(Integer idMatricula) {
        return listarTodos().stream().filter(m -> m.getMatricula().getId() == idMatricula).toList();
    }


 }
