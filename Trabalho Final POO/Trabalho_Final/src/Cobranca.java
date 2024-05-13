public class Cobranca extends Cliente {
    private String nomeCliente;
    private String emailCliente;
    private double valorCobrado;

    public Cobranca(String nomeCliente, String emailCliente, double valorCobrado) {
        super(emailCliente, nomeCliente, emailCliente);
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
        this.valorCobrado = valorCobrado;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public double getValorCobrado() {
        return valorCobrado;
    }

    public String getCpfCliente() {
        return getCpf();
    }
}

