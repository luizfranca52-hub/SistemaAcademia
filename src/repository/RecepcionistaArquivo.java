package repository;

import model.Recepcionista;
import model.Turno;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class RecepcionistaArquivo extends RecepcionistaRepository {
    private final Path arquivo;

    public RecepcionistaArquivo(String caminho) {
        this.arquivo = Paths.get(caminho);
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
                super.salvar(paraRecepcionista(linha)); //no map do repo geral
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler " + arquivo, e);
        }
    }

    @Override
    public void salvar(Recepcionista recepcionista) {
        super.salvar(recepcionista);
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

    // Formato: id;nome;cpf;dataNascimento;telefone;salarioBase;dataAdmissao;login;senha;turno;metaMatriculas;matriculasRealizadas
    private String paraLinha(Recepcionista r) {

        return String.join(";",
                String.valueOf(r.getId()),
                r.getNome(),
                r.getCpf(),
                r.getDataNascimento().toString(),
                r.getTelefone(),
                String.valueOf(r.getSalarioBase()),
                r.getDataAdmissao().toString(),
                r.getLogin(),
                r.getSenha(),
                r.getTurno().name(),
                String.valueOf(r.getMetaMatriculas()),
                String.valueOf(r.getMatriculasRealizadas())
        );
    }

    private Recepcionista paraRecepcionista(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length != 12) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        return new Recepcionista(Integer.parseInt(p[0]), p[1], p[2], LocalDate.parse(p[3]), p[4],
                Double.parseDouble(p[5]), LocalDate.parse(p[6]), p[7], p[8],
                Turno.valueOf(p[9]), Integer.parseInt(p[10]), Integer.parseInt(p[11]));
    }
}
