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

    // Formato: id;nome;cpf;dataNascimento;telefone;salarioBase;dataAdmissao;login;senha;cref;cargaHorariaSemanal
    private String paraLinha(Instrutor i) {

        return String.join(";",
                String.valueOf(i.getId()), i.getNome(), i.getCpf(),
                i.getDataNascimento().toString(), i.getTelefone(),
                String.valueOf(i.getSalarioBase()), i.getDataAdmissao().toString(),
                i.getLogin(), i.getSenha(),
                i.getCref(), String.valueOf(i.getCargaHorariaSemanal())
        );
    }

    private Instrutor paraInstrutor(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length != 11) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        return new Instrutor(Integer.parseInt(p[0]), p[1], p[2], LocalDate.parse(p[3]), p[4],
                Double.parseDouble(p[5]), LocalDate.parse(p[6]), p[7], p[8],
                p[9], Integer.parseInt(p[10]));
    }
}
