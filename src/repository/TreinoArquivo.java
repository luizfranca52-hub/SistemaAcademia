package repository;
import model.*;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TreinoArquivo extends TreinoRepository {

    private final Path arquivo;
    private final AlunoRepository alunoRepository;
    private final InstrutorRepository instrutorRepository;
    private final ExercicioRepository exercicioRepository;

    public TreinoArquivo(String caminho, AlunoRepository alunoRepository, InstrutorRepository instrutorRepository, ExercicioRepository exercicioRepository) {
        this.arquivo = Paths.get(caminho);
        this.alunoRepository = alunoRepository;
        this.instrutorRepository = instrutorRepository;
        this.exercicioRepository = exercicioRepository;
        carregar();
    }

    private void carregar() {
        if (!Files.exists(arquivo)) {
            return;
        }

        try {
            for (String linha : Files.readAllLines(arquivo)) {
                if (linha.isBlank()) {
                    continue;
                }

                super.salvar(paraTreino(linha));
            }

        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler " + arquivo, e);
        }
    }

    @Override
    public void salvar(Treino treino) {
        super.salvar(treino);
        gravar();
    }

    @Override
    public void remover(Integer id) {
        super.remover(id);
        gravar();
    }

    private void gravar() {
        List<String> linhas = listarTodos()
                .stream()
                .map(this::paraLinha)
                .toList();

        try {
            Files.write(arquivo, linhas);

        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao gravar " + arquivo, e);
        }
    }

    private String paraLinha(Treino treino) {

        String idsExercicios = treino.getExercicios().stream().map(e -> String.valueOf(e.getId())).collect(java.util.stream.Collectors.joining(","));

        return String.join(";",
                String.valueOf(treino.getId()),
                String.valueOf(treino.getAluno().getId()),
                String.valueOf(treino.getInstrutor().getId()),
                treino.getObjetivo(),
                idsExercicios
        );
    }

    private Treino paraTreino(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length != 5) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        Integer id = Integer.parseInt(p[0]);
        Integer idAluno = Integer.parseInt(p[1]);
        Integer idInstrutor = Integer.parseInt(p[2]);
        String objetivo = p[3];

        Aluno aluno = alunoRepository.buscarPorId(idAluno)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aluno não encontrado: " + idAluno
                ));

        Instrutor instrutor = instrutorRepository.buscarPorId(idInstrutor).orElseThrow(() -> new IllegalArgumentException("Instrutor não encontrado: " + idInstrutor));

        Treino treino = new Treino(id, aluno, instrutor, objetivo);

        if (!p[4].isBlank()) {

            String[] idsExercicios = p[4].split(",");
            for (String idExercicioTexto : idsExercicios) {
                Integer idExercicio = Integer.parseInt(idExercicioTexto);
                Exercicio exercicio = exercicioRepository.buscarPorId(idExercicio).orElseThrow(() -> new IllegalArgumentException("Exercicio não encontrado: " + idExercicio));

                treino.adicionarExercicio(exercicio);
            }
        }

        return treino;
    }
}