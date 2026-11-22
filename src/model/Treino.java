package model;

import java.util.ArrayList;

public class Treino {
    private int id;
    private String objetivo;
    private Aluno aluno;
    private Instrutor instrutor;
    private ArrayList<Exercicio> exercicios;

    public Treino(int id, Aluno aluno, Instrutor instrutor, String objetivo) {
        this.id = id;
        this.aluno = aluno;
        this.instrutor = instrutor;
        this.objetivo = objetivo;
        this.exercicios = new ArrayList<>();
    }

    public void adicionarExercicio(Exercicio exercicio) {
        exercicios.add(exercicio);
    }

    public void removerExercicio(Exercicio exercicio) {
        exercicios.remove(exercicio);
    }

    public ArrayList<Exercicio> getExercicios() {
        return new ArrayList<>(exercicios);
    }

    public void papelTreino(){
        System.out.println("Treino de "+ this.objetivo+ ":");
        System.out.println("Instrutor Responsável: "+ this.instrutor.getNome());
        for(int i = 0; i < exercicios.size(); i++){
            System.out.println((i+1) + ": "+ exercicios.get(i).getNome()+ " "+ exercicios.get(i).getEquipamento().getNome()+ " " + exercicios.get(i).getPeso()+ " kg "+ exercicios.get(i).getRepeticoes());
        }
    }

    public void setExercicios(ArrayList<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;

    }


}
