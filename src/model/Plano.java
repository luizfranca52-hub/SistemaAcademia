package model;

public class Plano {
    private int id;
    private String nome; //Premium...
    private double valor;
    private TipoPlano tipo;

    public Plano(int id, String nome, double valor, TipoPlano tipo) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.tipo = tipo;
    }

public int calcularDuracaoMeses(){
        return tipo.getMeses();
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoPlano getTipo() {
        return tipo;

    }
}
