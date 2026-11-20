package repository;

import model.Aluno;
import model.Instrutor;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class InstrutorArquivo extends InstrutorRepository{
    private final Path arquivo;

    public InstrutorArquivo(String caminho) {
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
                super.salvar(paraInstrutor(linha)); //no map do repo geral
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler " + arquivo, e);
        }}
    @Override
    public void salvar(Instrutor instrutor) {
        super.salvar(instrutor);
        gravar();
    }

    @Override
    public void remover(String cref) {
        super.remover(cref);
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
    private String paraLinha(Instrutor i) {

        return String.join(";",
                    i.getCref(), i.getLogin(), i.getSenha(),
                    String.valueOf(i.getSalario()), String.valueOf(i.getId()), i.getNome(),
                    i.getCpf(), i.getDataNascimento().toString(), i.getTelefone()
        );
    }

    private Instrutor paraInstrutor(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length != 9) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        return new Instrutor(p[0], p[1], p[2], Double.parseDouble(p[3]), Integer.parseInt(p[4]), p[5], p[6], LocalDate.parse(p[7]), p[8]);
    }
}
