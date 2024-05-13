import java.io.Serializable;

public class Cliente implements Serializable {
    private String cpf;
    private String nome;
    private String email;

    public Cliente(String cpf, String nome, String email){
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public void setEmail(String email){
        this.email = email;
    }

     public String getNome(){
        return nome;
    }

     public String getCpf(){
        return cpf;
    }

    public String getEmail(){
        return email;
    }
     
    public String toString() {
        return "Cliente{" +
                "CPF='" + cpf + '\'' +
                ", Nome='" + nome + '\'' +
                ", Email='" + email + '\'' +
                '}';
    }
}

