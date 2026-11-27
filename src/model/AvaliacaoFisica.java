    package model;

    import java.time.LocalDate;

    public class AvaliacaoFisica {
        private int id;
        private Pessoa avaliado; // pode ser aluno ou funcionário
        private Instrutor instrutor;
        private LocalDate data;
        private double peso;
        private double altura;

        public AvaliacaoFisica(int id, Pessoa avaliado, Instrutor instrutor, LocalDate data, double peso, double altura) {
            this.id = id;
            this.avaliado = avaliado;
            this.instrutor = instrutor;
            this.data = data;
            this.peso = peso;
            this.altura = altura;
        }

        public double calcularImc() {
            return peso / (altura * altura);
        }

        public String classificarImc() {
            double imc = calcularImc();
            if (imc < 18.5) return "Abaixo do peso";
            if (imc < 25) return "Peso normal";
            if (imc < 30) return "Sobrepeso";
            else return "Obesidade";
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Pessoa getAvaliado() {
            return avaliado;
        }

        public void setAvaliado(Pessoa avaliado) {
            this.avaliado = avaliado;
        }

        public Instrutor getInstrutor() {
            return instrutor;
        }

        public void setInstrutor(Instrutor instrutor) {
            this.instrutor = instrutor;
        }

        public LocalDate getData() {
            return data;
        }

        public void setData(LocalDate data) {
            this.data = data;
        }

        public double getPeso() {
            return peso;
        }

        public void setPeso(double peso) {
            this.peso = peso;
        }

        public double getAltura() {
            return altura;
        }

        public void setAltura(double altura) {
            this.altura = altura;
        }
    }
//id, alunoId, instrutorId, data, peso, altura, percentualGordura, calcularImc()