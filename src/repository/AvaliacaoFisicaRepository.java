package repository;
import model.AvaliacaoFisica;
import model.Pessoa;
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

    // histórico de avaliações de uma pessoa (aluno ou funcionário)
    // compara a classe tbm pq os ids se repetem entre os tipos de pessoa
    public List<AvaliacaoFisica> buscarPorAvaliado(Pessoa pessoa) {
        return listarTodos().stream()
                .filter(a -> a.getAvaliado().getClass() == pessoa.getClass() && a.getAvaliado().getId() == pessoa.getId())
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
