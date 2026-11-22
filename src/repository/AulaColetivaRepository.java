package repository;
import model.AulaColetiva;
import java.util.List;
import java.util.Optional;

public class AulaColetivaRepository extends RepositoryGeral<AulaColetiva, Integer> {

    @Override
    protected Integer getChave(AulaColetiva aula) {
        return aula.getId();
    }

    public Optional<AulaColetiva> buscarPorId(Integer id) {
        return buscarComChave(id);
    }

    public List<AulaColetiva> buscarPorModalidade(String modalidade) {
        return listarTodos().stream()
                .filter(a -> a.getModalidade().toLowerCase().contains(modalidade.toLowerCase())).toList();
    }

    public List<AulaColetiva> buscarPorInstrutor(Integer idInstrutor) {
        return listarTodos().stream().filter(a -> a.getInstrutor().getId() == idInstrutor).toList();
    }

    public boolean existePorId(Integer id) {
        return buscarComChave(id).isPresent();
    }
}