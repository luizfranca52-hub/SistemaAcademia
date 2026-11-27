package repository;

import model.Administrativo;
import model.Aluno;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class AdministrativoArquivo extends AdministrativoRepository{
    private final Path arquivo;

    public AdministrativoArquivo(String caminho) {
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
                super.salvar(paraAdministrativo(linha)); //no map do repo geral
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler " + arquivo, e);
        }}
    @Override
    public void salvar(Administrativo administrativo) {
        super.salvar(administrativo);
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

    // formato: id;nome;cpf;dataNascimento;telefone;salarioBase;dataAdmissao;cargo
    private String paraLinha(Administrativo a) {

        return String.join(";",
                String.valueOf(a.getId()),
                a.getNome(),
                a.getCpf(),
                a.getDataNascimento().toString(),
                a.getTelefone(),
                String.valueOf(a.getSalarioBase()),
                a.getDataAdmissao().toString(),
                a.getCargo()
        );
    }

    private Administrativo paraAdministrativo(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length != 8) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        return new Administrativo(Integer.parseInt(p[0]), p[1], p[2], LocalDate.parse(p[3]), p[4],
                Double.parseDouble(p[5]), LocalDate.parse(p[6]),
                p[7]);
    }
}
