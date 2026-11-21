package repository;

import model.Administrativo;
import model.Plano;
import model.TipoPlano;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class PlanoArquivo extends PlanoRepository{
    private final Path arquivo;

    public PlanoArquivo(String caminho) {
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
                super.salvar(paraPlano(linha)); //no map do repo geral
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler " + arquivo, e);
        }}
    @Override
    public void salvar(Plano plano) {
        super.salvar(plano);
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

    private String paraLinha(Plano p) {

        return String.join(";",
                String.valueOf(p.getId()),
                p.getNome(),
                String.valueOf(p.getValor()),
                p.getTipo().name()
        );
    }

    private Plano paraPlano(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length != 4) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        return new Plano(Integer.parseInt(p[0]), p[1], Double.parseDouble(p[2]), TipoPlano.valueOf(p[3]));
    }
}
