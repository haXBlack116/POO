import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


public class GerenciamentoAssinaturas implements Gerenciador<Assinatura>, Serializable {
   
    private List<Assinatura> listaAssinaturas = new ArrayList<>();

    public void cadastrar(Assinatura assinatura) {
        listaAssinaturas.add(assinatura);
    }

    public void editar(String codigo, Assinatura novaAssinatura) {
        for (int i = 0; i < listaAssinaturas.size(); i++) {
            Assinatura assinatura = listaAssinaturas.get(i);
            if (assinatura.getCodigoAssinatura().equals(codigo)) {
                listaAssinaturas.set(i, novaAssinatura);
                break;
            }
        }
    }

    public List<Assinatura> listar() {
        return listaAssinaturas;
    }

    public boolean remover(String codigo) {
    Iterator<Assinatura> iterator = listaAssinaturas.iterator();

    while (iterator.hasNext()) {
        Assinatura assinatura = iterator.next();
        if (assinatura.getCodigoAssinatura().equals(codigo)) {
            iterator.remove();
            return true; // Remoção bem-sucedida
        }
    }
    return false; // Assinatura não encontrada
}

    public boolean existe(String identificador) {
        for (Assinatura assinatura : listaAssinaturas) {
            if (assinatura.getCodigoAssinatura().equals(identificador)) {
                return true;
            }
        }
        return false;
    }
   
    public Assinatura buscarId(String identificador){
        for (Assinatura assinatura : listaAssinaturas) {
            if (assinatura.getCodigoAssinatura().equals(identificador)) {
                return assinatura;
            }
        }
        return null;
    }
   
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de Assinaturas:\n");
        for (Cliente assinatura : listaAssinaturas) {
            sb.append(assinatura.toString()).append("\n");
    }
        return sb.toString();
    }

    public List<Assinatura> obterAssinaturasPorCliente(String cpfCliente) {
        List<Assinatura> todasAssinaturas = listar();
        List<Assinatura> assinaturasCliente = new ArrayList<>();

        for (Assinatura assinatura : todasAssinaturas) {
            if (assinatura.getCpfCliente().equals(cpfCliente)) {
                assinaturasCliente.add(assinatura);
        }
    }
    return assinaturasCliente;
}
   
    public List<Assinatura> listarAssinantesDoAppPorNome(String nomeApp, GerenciamentoAplicativos gerenciamentoAplicativos) {
    List<Assinatura> assinantesDoApp = new ArrayList<>();

    for (Assinatura assinatura : listaAssinaturas) {
        if (assinatura.getMesAnoDeFimVigencia() == null) {
            String nomeAplicativo = gerenciamentoAplicativos.buscarId(assinatura.getCodigoApp()).getNome();
            if (nomeAplicativo.equals(nomeApp)) {
                assinantesDoApp.add(assinatura);
            }
        }
    }
    return assinantesDoApp;
}

    public void registrarInicioVigencia(String codigoAssinatura) {
        Assinatura assinatura = buscarId(codigoAssinatura);
        if (assinatura != null && assinatura.getMesAnoDeInicioVigencia() == null) {
            Calendar calendar = Calendar.getInstance();
            int mesAtual = calendar.get(Calendar.MONTH) + 1; // O mês é baseado em zero, então adicionamos 1
            int anoAtual = calendar.get(Calendar.YEAR);
            String mesAnoInicioVigencia = String.format("%02d/%04d", mesAtual, anoAtual);
            assinatura.setMesAnoDeInicioVigencia(mesAnoInicioVigencia);
        }
    }

    public void registrarEncerramentoVigencia(String codigoAssinatura) {
        Assinatura assinatura = buscarId(codigoAssinatura);
        if (assinatura != null && assinatura.getMesAnoDeFimVigencia() == null) {
            Calendar calendar = Calendar.getInstance();
            int mesAtual = calendar.get(Calendar.MONTH) + 1; // O mês é baseado em zero, então adicionamos 1
            int anoAtual = calendar.get(Calendar.YEAR);
            String mesAnoFimVigencia = String.format("%02d/%04d", mesAtual, anoAtual);
            assinatura.setMesAnoDeFimVigencia(mesAnoFimVigencia);
        }
    }

    private static final String arquivoAssinaturas = "assinaturas.txt";

    public void salvarEmArquivo() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(arquivoAssinaturas))) {
            outputStream.writeObject(listaAssinaturas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarDeArquivo() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(arquivoAssinaturas))) {
            @SuppressWarnings("unchecked")
            List<Assinatura> listaCarregada = (List<Assinatura>) inputStream.readObject();
            listaAssinaturas.clear();
            listaAssinaturas.addAll(listaCarregada);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

