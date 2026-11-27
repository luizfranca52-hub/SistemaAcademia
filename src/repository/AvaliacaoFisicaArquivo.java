package repository;
import model.*;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class AvaliacaoFisicaArquivo extends AvaliacaoFisicaRepository {

    private final Path arquivo;
    private final PessoaLocalizador pessoaLocalizador;
    private final InstrutorRepository instrutorRepository;

    public AvaliacaoFisicaArquivo(String caminho, PessoaLocalizador pessoaLocalizador, InstrutorRepository instrutorRepository) {
        this.arquivo = Paths.get(caminho);
        this.pessoaLocalizador = pessoaLocalizador;
        this.instrutorRepository = instrutorRepository;
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

                super.salvar(paraAvaliacao(linha));
            }

        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler " + arquivo, e);
        }
    }

    @Override
    public void salvar(AvaliacaoFisica avaliacao) {
        super.salvar(avaliacao);
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

    // formato: id;tipoAvaliado;idAvaliado;idInstrutor;data;peso;altura
    // (tipoAvaliado = ALUNO, INSTRUTOR, ADMINISTRATIVO ou RECEPCIONISTA)
    private String paraLinha(AvaliacaoFisica a) {
        return String.join(";",
                String.valueOf(a.getId()),
                pessoaLocalizador.tipoDe(a.getAvaliado()).name(),
                String.valueOf(a.getAvaliado().getId()),
                String.valueOf(a.getInstrutor().getId()),
                a.getData().toString(),
                String.valueOf(a.getPeso()),
                String.valueOf(a.getAltura())
        );
    }

    private AvaliacaoFisica paraAvaliacao(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length != 7) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        Integer id = Integer.parseInt(p[0]);
        Perfil tipoAvaliado = Perfil.valueOf(p[1]);
        Integer idAvaliado = Integer.parseInt(p[2]);
        Integer idInstrutor = Integer.parseInt(p[3]);
        LocalDate data = LocalDate.parse(p[4]);
        double peso = Double.parseDouble(p[5]);
        double altura = Double.parseDouble(p[6]);

        Pessoa avaliado = pessoaLocalizador.buscar(tipoAvaliado, idAvaliado);

        Instrutor instrutor = instrutorRepository.buscarPorId(idInstrutor).orElseThrow(() -> new IllegalArgumentException("Instrutor não encontrado: " + idInstrutor));

        return new AvaliacaoFisica(id, avaliado, instrutor, data, peso, altura);
    }
}