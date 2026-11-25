package repository;
import model.Matricula;
import model.Aluno;
import model.Plano;
import model.Recepcionista;
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
    private final RecepcionistaRepository recepcionistaRepository;

    // construtor antigo continua funcionando (sem recepcionista)
    public MatriculaArquivo(String caminho, AlunoRepository alunoRepository, PlanoRepository planoRepository) {
        this(caminho, alunoRepository, planoRepository, null);
    }

    public MatriculaArquivo(String caminho, AlunoRepository alunoRepository, PlanoRepository planoRepository,
                            RecepcionistaRepository recepcionistaRepository) {

        this.arquivo = Paths.get(caminho);
        this.alunoRepository = alunoRepository;
        this.planoRepository = planoRepository;
        this.recepcionistaRepository = recepcionistaRepository;

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

    // Formato: id;idAluno;idPlano;dataInicio;status;idRecepcionista;diasTrancados
    // (idRecepcionista = 0 quando a matrícula ainda não foi confirmada)
    private String paraLinha(Matricula m) {

        int idRecepcionista = 0;
        if (m.getRecepcionista() != null) {
            idRecepcionista = m.getRecepcionista().getId();
        }

        return String.join(";",
                String.valueOf(m.getId()),
                String.valueOf(m.getAluno().getId()),
                String.valueOf(m.getPlano().getId()),
                m.getDataInicio().toString(),
                m.getStatus().name(),
                String.valueOf(idRecepcionista),
                String.valueOf(m.getDiasTrancados())
        );
    }

    private Matricula paraMatricula(String linha) {

        String[] p = linha.split(";", -1);

        // aceita o formato antigo (5 campos) e o novo (7 campos)
        if (p.length != 5 && p.length != 7) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        Integer id = Integer.parseInt(p[0]);
        Integer idAluno = Integer.parseInt(p[1]);
        Integer idPlano = Integer.parseInt(p[2]);

        Aluno aluno = alunoRepository.buscarPorId(idAluno).orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + idAluno));

        Plano plano = planoRepository.buscarPorId(idPlano).orElseThrow(() -> new IllegalArgumentException("Plano não encontrado: " + idPlano));

        LocalDate dataInicio = LocalDate.parse(p[3]);
        StatusMatricula status = StatusMatricula.valueOf(p[4]);

        Recepcionista recepcionista = null;
        int diasTrancados = 0;

        if (p.length == 7) {
            Integer idRecepcionista = Integer.parseInt(p[5]);
            if (idRecepcionista != 0 && recepcionistaRepository != null) {
                recepcionista = recepcionistaRepository.buscarPorId(idRecepcionista).orElseThrow(() -> new IllegalArgumentException("Recepcionista não encontrada: " + idRecepcionista));
            }
            diasTrancados = Integer.parseInt(p[6]);
        }

        return new Matricula(id, aluno, plano, dataInicio, status, recepcionista, diasTrancados);
    }
}

