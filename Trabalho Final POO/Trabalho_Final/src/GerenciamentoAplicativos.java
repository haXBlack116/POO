import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GerenciamentoAplicativos implements Gerenciador<Aplicativo>, Serializable {
   
    private List<Aplicativo> listaAplicativos = new ArrayList<>();
   
    public void cadastrar(Aplicativo aplicativo) {
        listaAplicativos.add(aplicativo);
    }

    public void editar(String codigo, Aplicativo novoAplicativo) {
        for (int i = 0; i < listaAplicativos.size(); i++) {
            Aplicativo aplicativo = listaAplicativos.get(i);
            if (aplicativo.getCodigo().equals(codigo)) {
                listaAplicativos.set(i, novoAplicativo);
                break;
            }
        }
    }

    public List<Aplicativo> listar() {
        return listaAplicativos;
    }

    public boolean remover(String codigo) {
    Iterator<Aplicativo> iterator = listaAplicativos.iterator();

    while (iterator.hasNext()) {
        Aplicativo aplicativo = iterator.next();
        if (aplicativo.getCodigo().equals(codigo)) {
            iterator.remove();
            return true; // Remoção bem-sucedida
        }
    }
    return false; // Aplicativo não encontrado
}

    public boolean existe(String identificador) {
    for (Aplicativo aplicativo : listaAplicativos) {
        if (aplicativo.getCodigo().equals(identificador)) {
            return true;
        }
    }
    return false;
}

    public Aplicativo buscarId(String identificador){
    for (Aplicativo aplicativo : listaAplicativos) {
        if (aplicativo.getCodigo().equals(identificador)) {
            return aplicativo;
        }
    }
    return null;
}

    public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Lista de Aplicativos:\n");
    for (Aplicativo aplicativo : listaAplicativos) {
        sb.append(aplicativo.toString()).append("\n");
    }
    return sb.toString();
    }

    public double obterValorMensal(String codigoApp) {
    if (codigoApp == null) {
        System.out.println("Código de aplicativo nulo.");
        return 0.0; 
    }

    for (Aplicativo aplicativo : listaAplicativos) {
        if (aplicativo.getCodigo().equals(codigoApp)) {
            try {
                return Double.parseDouble(aplicativo.getValMensal());
            } catch (NumberFormatException e) {
                System.out.println("Erro ao converter valor mensal para double: " + aplicativo.getValMensal());
                return 0.0; 
            }
        }
    }
    System.out.println("Código de aplicativo não encontrado: " + codigoApp);
    return 0.0; 
}

    private static final String arquivoAplicativos = "aplicativos.txt";

    public void salvarEmArquivo() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(arquivoAplicativos))) {
            outputStream.writeObject(listaAplicativos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarDeArquivo() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(arquivoAplicativos))) {
            @SuppressWarnings("unchecked") 
            List<Aplicativo> listaCarregada = (List<Aplicativo>) inputStream.readObject();
            listaAplicativos.clear();
            listaAplicativos.addAll(listaCarregada);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}




