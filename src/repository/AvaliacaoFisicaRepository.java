package repository;
import model.AvaliacaoFisica;
import java.util.List;
import java.util.Optional;

public class AvaliacaoFisicaRepository extends RepositoryGeral<AvaliacaoFisica, Integer> {

    @Override
    protected Integer getChave(AvaliacaoFisica avaliacao) {
        return avaliacao.getId();
    }

    public Optional<AvaliacaoFisica> buscarPorId(Integer id) {
        return buscarComChave(id);
    }

    public List<AvaliacaoFisica> buscarPorAluno(Integer idAluno) {
        return listarTodos().stream()
                .filter(a -> a.getAluno().getId() == idAluno)
                .toList();
    }

    public List<AvaliacaoFisica> buscarPorInstrutor(Integer idInstrutor) {
        return listarTodos().stream()
                .filter(a -> a.getInstrutor().getId() == idInstrutor)
                .toList();
    }

    public boolean existePorId(Integer id) {
        return buscarComChave(id).isPresent();
    }
}
