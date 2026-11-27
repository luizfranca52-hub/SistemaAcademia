package repository;

import model.Perfil;
import model.Pessoa;
import model.Usuario;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UsuarioArquivo extends UsuarioRepository {
    private final Path arquivo;
    private final PessoaLocalizador pessoaLocalizador;

    public UsuarioArquivo(String caminho, PessoaLocalizador pessoaLocalizador) {
        this.arquivo = Paths.get(caminho);
        this.pessoaLocalizador = pessoaLocalizador;
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
                super.salvar(paraUsuario(linha)); //no map do repo geral
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler " + arquivo, e);
        }
    }

    // salvar de novo dps de um login errado, pra guardar as tentativas
    @Override
    public void salvar(Usuario usuario) {
        super.salvar(usuario);
        gravar();
    }

    @Override
    public void remover(String login) {
        super.remover(login);
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

    // formato: login;senhaHash;perfil;idPessoa;tentativasErradas
    // (o perfil diz em qual repositório a pessoa está)
    private String paraLinha(Usuario u) {

        return String.join(";",
                u.getLogin(),
                u.getSenhaHash(),
                u.getPerfil().name(),
                String.valueOf(u.getPessoa().getId()),
                String.valueOf(u.getTentativasErradas())
        );
    }

    private Usuario paraUsuario(String linha) {

        String[] p = linha.split(";", -1);

        if (p.length != 5) {
            throw new IllegalArgumentException("Linha inválida no arquivo: " + linha);
        }

        Perfil perfil = Perfil.valueOf(p[2]);
        Pessoa pessoa = pessoaLocalizador.buscar(perfil, Integer.parseInt(p[3]));

        return new Usuario(p[0], p[1], perfil, pessoa, Integer.parseInt(p[4]));
    }
}
