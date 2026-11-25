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
    private final MatriculaRepository matriculaRepository;

    // não precisa mais do AlunoRepository: o aluno vem pela matrícula
    public PagamentoArquivo(String caminho, MatriculaRepository matriculaRepository) {
        this.arquivo = Paths.get(caminho);
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


    // Formato: id;idMatricula;valor;dataPagamento;TIPO;extras
    //   PIX      -> ...;PIX;percentualDesconto
    //   CARTAO   -> ...;CARTAO;parcelas;credito
    //   DINHEIRO -> ...;DINHEIRO;valorRecebido
    private String paraLinha(Pagamento p) {

        String inicio = String.join(";",
                String.valueOf(p.getId()),
                String.valueOf(p.getMatricula().getId()),
                String.valueOf(p.getValor()),
                p.getDataPagamento().toString()
        );

        if (p instanceof PagamentoPix) {
            PagamentoPix pix = (PagamentoPix) p;
            return inicio + ";PIX;" + pix.getPercentualDesconto();
        }
        if (p instanceof PagamentoCartao) {
            PagamentoCartao cartao = (PagamentoCartao) p;
            return inicio + ";CARTAO;" + cartao.getParcelas() + ";" + cartao.isCredito();
        }
        PagamentoDinheiro dinheiro = (PagamentoDinheiro) p;
        return inicio + ";DINHEIRO;" + dinheiro.getValorRecebido();
    }

    private Pagamento paraPagamento(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length < 6) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        int id = Integer.parseInt(p[0]);
        Integer idMatricula = Integer.parseInt(p[1]);
        Matricula matricula = matriculaRepository.buscarPorId(idMatricula).orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada: " + idMatricula));
        double valor = Double.parseDouble(p[2]);
        LocalDate dataPagamento = LocalDate.parse(p[3]);
        String tipo = p[4];

        if (tipo.equals("PIX")) {
            return new PagamentoPix(id, matricula, valor, dataPagamento, Double.parseDouble(p[5]));
        }
        if (tipo.equals("CARTAO")) {
            return new PagamentoCartao(id, matricula, valor, dataPagamento, Integer.parseInt(p[5]), Boolean.parseBoolean(p[6]));
        }
        if (tipo.equals("DINHEIRO")) {
            return new PagamentoDinheiro(id, matricula, valor, dataPagamento, Double.parseDouble(p[5]));
        }
        throw new IllegalArgumentException("Tipo de pagamento desconhecido: " + tipo);
    }
}
