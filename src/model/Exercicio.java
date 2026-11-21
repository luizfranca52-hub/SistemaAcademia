package model;

public class Exercicio {
    private String nome;
    private GrupoMuscular grupoMuscular;
    private int peso;
    private Equipamento equipamento;
    private int repeticoes;

    public Exercicio(String nome, GrupoMuscular grupoMuscular, int peso, int repeticoes, Equipamento equipamento) {
        this.nome = nome;
        this.grupoMuscular = grupoMuscular;
        this.peso = peso;
        this.equipamento = equipamento;
        this.repeticoes = repeticoes;
    }

    public Exercicio(String nome, GrupoMuscular grupoMuscular, int peso, int repeticoes){
        this(nome, grupoMuscular, peso, repeticoes, new Equipamento(-1, "Livre", StatusEquipamento.DISPONIVEL));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public GrupoMuscular getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(GrupoMuscular grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }
}
