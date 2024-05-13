import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GerenciamentoClientes implements Gerenciador<Cliente>, Serializable {
   
private List<Cliente> listaClientes = new ArrayList<>();

    public void cadastrar(Cliente cliente){
        listaClientes.add(cliente);
    }

    public void editar(String cpf, Cliente newCliente){
    for(int i = 0; i < listaClientes.size(); i++){
        Cliente cliente = listaClientes.get(i);
            if(cliente.getCpf().equals(cpf)){
                listaClientes.set(i, newCliente);
                break;
        }
    }
}


    public List<Cliente> listar(){
        return listaClientes;
    }


    public boolean remover(String codigo) {
    Iterator<Cliente> iterator = listaClientes.iterator();


    while (iterator.hasNext()) {
        Cliente cliente = iterator.next();
        if (cliente.getCpf().equals(codigo)) {
            iterator.remove();
            return true; // Remoção bem-sucedida
        }
    }
    return false; // Cliente não encontrado
}


    public boolean existe(String identificador) {
    for (Cliente cliente : listaClientes) {
        if (cliente.getCpf().equals(identificador)) {
            return true;
        }
    }
    return false;
}

    public Cliente buscarId(String identificador){
    for (Cliente cliente : listaClientes) {
        if (cliente.getCpf().equals(identificador)) {
            return cliente;
        }
    }
    return null;
}

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de Clientes:\n");
        for (Cliente cliente : listaClientes) {
            sb.append(cliente.toString()).append("\n");
    }
    return sb.toString();
}

    private static final String arquivoClientes = "clientes.txt";

    public void salvarEmArquivo() {
    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(arquivoClientes))) {
            outputStream.writeObject(listaClientes);
    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarDeArquivo() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(arquivoClientes))) {
            @SuppressWarnings("unchecked")
            List<Cliente> listaCarregada = (List<Cliente>) inputStream.readObject();
            listaClientes.clear();
            listaClientes.addAll(listaCarregada);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}



