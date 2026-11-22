package model;

public class Exercicio {
    private int id;
    private String nome;
    private GrupoMuscular grupoMuscular;
    private double peso;
    private Equipamento equipamento;
    private int repeticoes;

    public Exercicio(int id, String nome, GrupoMuscular grupoMuscular, double peso, Equipamento equipamento, int repeticoes) {
        this.id = id;
        this.nome = nome;
        this.grupoMuscular = grupoMuscular;
        this.peso = peso;
        this.equipamento = equipamento;
        this.repeticoes = repeticoes;
    }

    public Exercicio(int id, String nome, GrupoMuscular grupoMuscular, int peso, int repeticoes){
        this(id, nome, grupoMuscular, peso, new Equipamento(-1, "Livre", StatusEquipamento.DISPONIVEL), repeticoes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getPeso() {
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
