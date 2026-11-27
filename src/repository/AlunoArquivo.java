package repository;

import model.Aluno;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

public class AlunoArquivo extends AlunoRepository {

    private final Path arquivo;

    public AlunoArquivo(String caminho) {
        this.arquivo = Paths.get(caminho);
        carregar();
    }

    private void carregar() {
        if (!Files.exists(arquivo)) {
            return;}

        try {
            for (String linha : Files.readAllLines(arquivo)) {
                if (linha.isBlank()) {
                    continue;
                }
                super.salvar(paraAluno(linha)); //no map do repo geral
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler " + arquivo, e);
        }}
    @Override
    public void salvar(Aluno aluno) {
        super.salvar(aluno);
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

    // Formato que a classe pede: id;nome;cpf;dataNascimento;telefone;login;senha
    // formato: id;nome;cpf;dataNascimento;telefone;responsavel
    private String paraLinha(Aluno a) {

        String responsavel = "";
        if (a.getResponsavel() != null) {
            responsavel = a.getResponsavel();
        }

        return String.join(";",
                String.valueOf(a.getId()),
                a.getNome(),
                a.getCpf(),
                a.getDataNascimento().toString(),
                a.getTelefone(),
                responsavel
        );
    }

    private Aluno paraAluno(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length != 6) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        return new Aluno(Integer.parseInt(p[0]), p[1], p[2], LocalDate.parse(p[3]), p[4], p[5]);
    }
}