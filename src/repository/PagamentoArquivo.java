package repository;

import model.*;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class PagamentoArquivo extends PagamentoRepository {
    private final Path arquivo;
    private final AlunoRepository alunoRepository;
    private final MatriculaRepository matriculaRepository;

    public PagamentoArquivo(String caminho, AlunoRepository alunoRepository, MatriculaRepository matriculaRepository) {
        this.arquivo = Paths.get(caminho);
        this.alunoRepository = alunoRepository;
        this.matriculaRepository = matriculaRepository;

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

                super.salvar(paraPagamento(linha));
            }

        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler " + arquivo, e);
        }
    }

    @Override
    public void salvar(Pagamento pagamento) {
        super.salvar(pagamento);
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


    private String paraLinha(Pagamento p) {

        return String.join(";",
                  String.valueOf(p.getId()),
                  String.valueOf(p.getAluno().getId()),
                  String.valueOf(p.getMatricula().getId()),
                  String.valueOf(p.getValor()),
                  p.getDataPagamento().toString(),
                  p.getForma().name()
        );
    }

    private Pagamento paraPagamento(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length != 6) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        Integer id = Integer.parseInt(p[0]);
        Integer idAluno = Integer.parseInt(p[1]);
        Integer idMatricula = Integer.parseInt(p[2]);

        Aluno aluno = alunoRepository.buscarPorId(idAluno).orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + idAluno));
        Matricula matricula= matriculaRepository.buscarPorId(idMatricula).orElseThrow(() -> new IllegalArgumentException("Plano não encontrado: " + idMatricula));
        double valor = Double.parseDouble(p[3]);
        LocalDate dataPagamento = LocalDate.parse(p[4]);
        FormaPagamento forma = FormaPagamento.valueOf(p[5]);

         return new Pagamento(id, aluno, matricula, valor, dataPagamento, forma);
    }
}
