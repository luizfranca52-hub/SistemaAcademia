package repository;

import model.*;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class ExercicioArquivo extends ExercicioRepository{
    private final Path arquivo;
    private final EquipamentoRepository equipamentoRepository;

    public ExercicioArquivo(String caminho, EquipamentoRepository equipamentoRepository) {
        this.arquivo = Paths.get(caminho);
        this.equipamentoRepository = equipamentoRepository;
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
                super.salvar(paraExercicio(linha)); //no map do repo geral
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler " + arquivo, e);
        }}
    @Override
    public void salvar(Exercicio exercicio) {
        super.salvar(exercicio);
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
    private String paraLinha(Exercicio e) {

        return String.join(";",
                  String.valueOf(e.getId()),
                  e.getNome(),
                  e.getGrupoMuscular().name(),
                  String.valueOf(e.getPeso()),
                String.valueOf(e.getEquipamento().getId()),
                String.valueOf(e.getRepeticoes())

        );
    }

    private Exercicio paraExercicio(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length != 6) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        Integer id = Integer.parseInt(p[0]);
        String nome = p[1];
        GrupoMuscular grupoMuscular = GrupoMuscular.valueOf(p[2]);
        double peso = Double.parseDouble(p[3]);
        Integer idEquipamento = Integer.parseInt(p[4]);
        int repeticoes = Integer.parseInt(p[5]);

        Equipamento equipamento = equipamentoRepository.buscarPorId(idEquipamento).orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado: " + idEquipamento));

        return new Exercicio(id, nome, grupoMuscular, peso, equipamento, repeticoes);
    }
}
