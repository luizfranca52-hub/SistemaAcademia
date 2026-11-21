package repository;
import model.Matricula;
import model.Aluno;
import model.Plano;
import model.StatusMatricula;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class MatriculaArquivo extends MatriculaRepository {

    private final Path arquivo;
    private final AlunoRepository alunoRepository;
    private final PlanoRepository planoRepository;

    public MatriculaArquivo(String caminho, AlunoRepository alunoRepository, PlanoRepository planoRepository) {

        this.arquivo = Paths.get(caminho);
        this.alunoRepository = alunoRepository;
        this.planoRepository = planoRepository;

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

                super.salvar(paraMatricula(linha));
            }

        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler " + arquivo, e);
        }
    }

    @Override
    public void salvar(Matricula matricula) {
        super.salvar(matricula);
        gravar();
    }

    @Override
    public void remover(Integer id) {
        super.remover(id);
        gravar();
    }

    private void gravar() {
        List<String> linhas = listarTodos().stream()
                .map(this::paraLinha)
                .toList();

        try {
            Files.write(arquivo, linhas);

        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao gravar " + arquivo, e);
        }
    }

    // Formato: id;idAluno;idPlano;dataInicio;status
    private String paraLinha(Matricula m) {

        return String.join(";",
                String.valueOf(m.getId()),
                String.valueOf(m.getAluno().getId()),
                String.valueOf(m.getPlano().getId()),
                m.getDataInicio().toString(),
                m.getStatus().name()
        );
    }

    private Matricula paraMatricula(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length != 5) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        Integer id = Integer.parseInt(p[0]);
        Integer idAluno = Integer.parseInt(p[1]);
        Integer idPlano = Integer.parseInt(p[2]);

        Aluno aluno = alunoRepository.buscarPorId(idAluno).orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + idAluno));

        Plano plano = planoRepository.buscarPorId(idPlano).orElseThrow(() -> new IllegalArgumentException("Plano não encontrado: " + idPlano));

        LocalDate dataInicio = LocalDate.parse(p[3]);
        StatusMatricula status = StatusMatricula.valueOf(p[4]);
        return new Matricula(id, aluno, plano, dataInicio, status);
    }
}

