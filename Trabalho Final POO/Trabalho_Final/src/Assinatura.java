public class Assinatura extends Cliente  {
    private String codigoAssinatura;
    private String codigoDoApp;
    private String mesAnoDeInicioVigencia;
    private String mesAnoDeFimVigencia;

    public Assinatura(String cpf, String nome, String email, String codigoAssinatura, String codigoApp) {
        super(cpf, nome, email);
        this.codigoAssinatura = codigoAssinatura;
        this.codigoDoApp = codigoApp;
    }

    public void setCodigoAssinatura(String codigoAssinatura){
        this.codigoAssinatura = codigoAssinatura;
    }

    public void setCodigoDoApp(String codigoDoApp){
        this.codigoDoApp = codigoDoApp;
    }

    public void setMesAnoDeInicioVigencia(String mesAnoDeInicioVigencia){
        this.mesAnoDeInicioVigencia = mesAnoDeInicioVigencia;
    }

    public void setMesAnoDeFimVigencia(String mesAnoDeFimVigencia){
        this.mesAnoDeFimVigencia = mesAnoDeFimVigencia;
    }

    public String getCodigoAssinatura(){
        return codigoAssinatura;
    }

     public String getCodigoApp(){
        return codigoDoApp;
    }

    public String getCpfCliente() {
        return getCpf();
    }

     public String getMesAnoDeInicioVigencia(){
        return mesAnoDeInicioVigencia;
    }

    public String getMesAnoDeFimVigencia(){
        return mesAnoDeFimVigencia;
    }

    public String toString() {
        return "Assinatura{" +
                "Codigo da Assinatura ='" + codigoAssinatura + '\'' +
                ", Codigo do Aplicativo ='" + codigoDoApp + '\'' +
                ", Mes e Ano de Inicio da Vigencia ='" + mesAnoDeInicioVigencia + '\'' +
                ", Mes e Ano do Fim da Vigencia ='" + mesAnoDeFimVigencia + '\'' +
                '}';
    }
}

