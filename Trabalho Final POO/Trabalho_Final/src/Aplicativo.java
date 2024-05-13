import java.io.Serializable;

public class Aplicativo implements Serializable {
    private String codigo;
    private String nome;
    private String sistemaOperacional;
    private String valorMensalDaLicensa;

    public Aplicativo(String codigo, String nome, String so, String licensa){
        this.codigo = codigo;
        this.nome = nome;
        this.sistemaOperacional = so;
        this.valorMensalDaLicensa = licensa;
    }

    public void setCodigo(String codigo){
        this.codigo = codigo;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setSO(String sistemaOperacional){
        this.sistemaOperacional = sistemaOperacional;
    }

    public void setValMensal(String valorMensalDaLicensa){
        this.valorMensalDaLicensa = valorMensalDaLicensa;
    }

    public String getCodigo(){
        return codigo;
    }

     public String getNome(){
        return nome;
    }

     public String getSO(){
        return sistemaOperacional;
    }

     public String getValMensal(){
        return valorMensalDaLicensa;
    }

    public String toString() {
        return "Assinatura{" +
                "Codigo do Aplicativo ='" + codigo + '\'' +
                ", Nome do Aplicativo ='" + nome + '\'' +
                ", SO ='" + sistemaOperacional + '\'' +
                ", Valor mensal da licensa ='" + valorMensalDaLicensa + '\'' +
                '}';
    }
}

