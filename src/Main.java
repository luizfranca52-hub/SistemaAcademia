
import model.*;
import repository.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        // ==========================================
        // TESTE 1 - CARREGAR OS REPOSITÓRIOS
        // ==========================================

        AlunoArquivo alunoRepo = new AlunoArquivo("alunos.txt");

        PlanoArquivo planoRepo = new PlanoArquivo("Plano.txt");

        MatriculaArquivo matriculaRepo = new MatriculaArquivo(
                "matriculas_teste.txt",
                alunoRepo,
                planoRepo
        );

        System.out.println("===== TESTE 1: REPOSITÓRIOS =====");

        System.out.println("Alunos carregados: " + alunoRepo.listarTodos().size());
        System.out.println("Planos carregados: " + planoRepo.listarTodos().size());


        // ==========================================
        // TESTE 2 - CRIAR MATRÍCULA
        // ==========================================

        try {
            Files.deleteIfExists(Paths.get("matriculas_teste.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Recriar o repositório com arquivo vazio
        matriculaRepo = new MatriculaArquivo(
                "matriculas_teste.txt",
                alunoRepo,
                planoRepo
        );

        // Substitua pelos IDs existentes nos seus arquivos
        Aluno aluno = alunoRepo.buscarPorId(1).orElseThrow();
        Plano plano = planoRepo.buscarPorId(2).orElseThrow();

        Matricula m1 = new Matricula(
                1,
                aluno,
                plano,
                LocalDate.of(2026, 11, 21),
                StatusMatricula.ATIVA
        );

        matriculaRepo.salvar(m1);

        System.out.println("\n===== TESTE 2: SALVAR MATRÍCULA =====");

        System.out.println("Matrícula salva? " + matriculaRepo.existePorId(1));


        // ==========================================
        // TESTE 3 - BUSCAR POR ID
        // ==========================================

        System.out.println("\n===== TESTE 3: BUSCAR POR ID =====");

        System.out.println("ID 1: " + matriculaRepo.buscarPorId(1));
        System.out.println("ID 999: " + matriculaRepo.buscarPorId(999));


        // ==========================================
        // TESTE 4 - BUSCAR POR ALUNO
        // ==========================================

        System.out.println("\n===== TESTE 4: BUSCAR POR ALUNO =====");

        System.out.println(
                matriculaRepo.buscarPorAluno(aluno.getId())
        );


        // ==========================================
        // TESTE 5 - BUSCAR POR PLANO
        // ==========================================

        System.out.println("\n===== TESTE 5: BUSCAR POR PLANO =====");

        System.out.println(
                matriculaRepo.buscarPorPlano(plano.getId())
        );


        // ==========================================
        // TESTE 6 - BUSCAR POR STATUS
        // ==========================================

        System.out.println("\n===== TESTE 6: BUSCAR POR STATUS =====");

        System.out.println(
                matriculaRepo.buscarPorStatus(StatusMatricula.ATIVA)
        );


        // ==========================================
        // TESTE 7 - LISTAR ATIVAS
        // ==========================================

        System.out.println("\n===== TESTE 7: LISTAR ATIVAS =====");

        System.out.println(matriculaRepo.listarAtivas());


        // ==========================================
        // TESTE 8 - VERIFICAR ARQUIVO
        // ==========================================

        System.out.println("\n===== TESTE 8: CONTEÚDO DO ARQUIVO =====");

        try {
            Files.readAllLines(Paths.get("matriculas_teste.txt"))
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // ==========================================
        // TESTE 9 - RECARREGAR ARQUIVO
        // ==========================================

        System.out.println("\n===== TESTE 9: RECARREGAR =====");

        MatriculaArquivo repo2 = new MatriculaArquivo(
                "matriculas_teste.txt",
                alunoRepo,
                planoRepo
        );

        System.out.println("Matrícula existe? " + repo2.existePorId(1));

        System.out.println("Matrícula recuperada: " + repo2.buscarPorId(1));

        System.out.println("Aluno recuperado: " +
                repo2.buscarPorId(1).orElseThrow().getAluno().getNome());

        System.out.println("Plano recuperado: " +
                repo2.buscarPorId(1).orElseThrow().getPlano().getNome());


//        // ==========================================
//        // TESTE 10 - REMOVER
//        // ==========================================
//
//        System.out.println("\n===== TESTE 10: REMOVER =====");
//
//        repo2.remover(1);
//
//        System.out.println("Existe após remover? " + repo2.existePorId(1));
//
//
//        // ==========================================
//        // TESTE 11 - VERIFICAR PERSISTÊNCIA DA REMOÇÃO
//        // ==========================================
//
//        System.out.println("\n===== TESTE 11: RECARREGAR APÓS REMOÇÃO =====");
//
//        MatriculaArquivo repo3 = new MatriculaArquivo(
//                "matriculas_teste.txt",
//                alunoRepo,
//                planoRepo
//        );
//
//        System.out.println("Existe após recarregar? " + repo3.existePorId(1));
//    }
}}
