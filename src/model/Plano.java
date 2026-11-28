package model;

public class Plano {
    private int id;
    private String nome; //Premium...
    private double valor;
    private TipoPlano tipo;
    private StatusPlano status;

        // plano novo já nasce ATIVO
    public Plano(int id, String nome, double valor, TipoPlano tipo) {
        this(id, nome, valor, tipo, StatusPlano.ATIVO);
    }

    public Plano(int id, String nome, double valor, TipoPlano tipo, StatusPlano status) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.tipo = tipo;
        this.status = status;
    }

    // plano q a academia parou de vender n é apagado, só descontinuado
    // (as matrículas antigas continuam apontando pra ele)
    public void descontinuar() {
        this.status = StatusPlano.DESCONTINUADO;
    }

    public void reativar() {
        this.status = StatusPlano.ATIVO;
    }

    // descontinuado n aceita matrícula nova nem renovação
    public boolean aceitaNovaMatricula() {
        return status == StatusPlano.ATIVO;
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

    public StatusPlano getStatus() {
        return status;
    }
}
