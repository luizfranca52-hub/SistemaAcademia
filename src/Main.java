import model.*;
import repository.AlunoArquivo;
import repository.InstrutorArquivo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class Main {
    void main() {
        AlunoArquivo repositorio = new AlunoArquivo("alunos.txt");

        Aluno aluno1 = new Aluno(
                1, "Luiz", "12345678900",
                LocalDate.of(2005, 5, 20),
                "84999999999", "luiz123", "senha123"
        );

        Aluno aluno2 = new Aluno(
                2, "Carlos", "98765432100",
                LocalDate.of(2004, 8, 15),
                "84988888888", "carlos123", "senha456"
        );

    repositorio.salvar(aluno2);
    repositorio.salvar(aluno1);


    InstrutorArquivo repo = new InstrutorArquivo("instrutores1.txt");
    Instrutor instrutor1 = new Instrutor("12", "sarah", "aba", 1234.00, 123, "Sarah", "1234567", LocalDate.of(2005, 5, 20) ,"12345678");
        Instrutor instrutor3 = new Instrutor("10", "adriana", "abi", 123.00, 153, "Adriana", "1234867", LocalDate.of(2005, 5, 20) ,"12345678");

        Instrutor instrutor2 = new Instrutor("11", "rafinha", "abo", 123.00, 153, "Rafael", "1234867", LocalDate.of(2005, 5, 20) ,"12345678");
    repo.salvar(instrutor1);
    repo.salvar(instrutor2);
    repo.salvar(instrutor3);

    repo.remover("12");
    }
}