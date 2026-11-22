package repository;

import model.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AulaColetivaArquivo extends AulaColetivaRepository {

    private final Path arquivo;
    private final InstrutorRepository instrutorRepository;
    private final AlunoRepository alunoRepository;

    public AulaColetivaArquivo(String caminho, InstrutorRepository instrutorRepository, AlunoRepository alunoRepository) {
        this.arquivo = Paths.get(caminho);
        this.instrutorRepository = instrutorRepository;
        this.alunoRepository = alunoRepository;
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

                super.salvar(paraAulaColetiva(linha));
            }

        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler " + arquivo, e);
        }
    }

    @Override
    public void salvar(AulaColetiva aula) {
        super.salvar(aula);
        gravar();
    }

    @Override
    public void remover(Integer id) {
        super.remover(id);
        gravar();
    }

    private void gravar() {
        List<String> linhas = listarTodos().stream().map(this::paraLinha).toList();

        try {
            Files.write(arquivo, linhas);
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao gravar " + arquivo, e);
        }
    }

    private String paraLinha(AulaColetiva aula) {

        String dias = aula.getDias().stream().map(DayOfWeek::name).collect(Collectors.joining(","));

        String alunos = aula.getAlunos().stream().map(a -> String.valueOf(a.getId())).collect(Collectors.joining(","));

        return String.join(";",
                String.valueOf(aula.getId()),
                aula.getModalidade(),
                String.valueOf(aula.getInstrutor().getId()),
                dias,
                aula.getHorario().toString(),
                String.valueOf(aula.getVagas()),
                alunos
        );
    }

    private AulaColetiva paraAulaColetiva(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length != 7) {
            throw new IllegalArgumentException(
                    "Linha inválida no arquivo: " + linha
            );
        }

        Integer id = Integer.parseInt(p[0]);
        String modalidade = p[1];
        Integer idInstrutor = Integer.parseInt(p[2]);
        LocalTime horario = LocalTime.parse(p[4]);
        int vagas = Integer.parseInt(p[5]);

        Instrutor instrutor = instrutorRepository.buscarPorId(idInstrutor).orElseThrow(() -> new IllegalArgumentException("Instrutor não encontrado: " + idInstrutor));

        List<DayOfWeek> dias = new ArrayList<>();

        if (!p[3].isBlank()) {
            String[] diasTexto = p[3].split(",");
            for (String dia : diasTexto) {
                dias.add(DayOfWeek.valueOf(dia));
            }
        }

        AulaColetiva aula = new AulaColetiva(id, modalidade, instrutor, dias, horario, vagas);

        if (!p[6].isBlank()) {
            String[] idsAlunos = p[6].split(",");
            for (String idAlunoTexto : idsAlunos) {

                Integer idAluno = Integer.parseInt(idAlunoTexto);

                Aluno aluno = alunoRepository.buscarPorId(idAluno)
                        .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + idAluno));

                boolean matriculou = aula.matricularAluno(aluno);

                if (!matriculou) {
                    throw new IllegalArgumentException(
                            "Não foi possível matricular o aluno " + idAluno
                                    + " na aula " + id
                    );
                }
            }
        }

        return aula;
    }
}