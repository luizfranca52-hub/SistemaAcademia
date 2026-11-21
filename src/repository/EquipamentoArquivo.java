package repository;

import model.Aluno;
import model.Equipamento;
import model.FormaPagamento;
import model.StatusEquipamento;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class EquipamentoArquivo extends EquipamentoRepository{
    private final Path arquivo;

    public EquipamentoArquivo(String caminho) {
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
                super.salvar(paraEquipamento(linha)); //no map do repo geral
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler " + arquivo, e);
        }}
    @Override
    public void salvar(Equipamento equipamento) {
        super.salvar(equipamento);
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
    private String paraLinha(Equipamento e) {

        return String.join(";",
                String.valueOf(e.getId()),
                e.getNome(),
                e.getStatus().name()
        );
    }

    private Equipamento paraEquipamento(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length != 3) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        return new Equipamento(Integer.parseInt(p[0]), p[1], StatusEquipamento.valueOf(p[2]));
    }
}
