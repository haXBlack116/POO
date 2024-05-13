import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerenciamentoCobranca {
    private GerenciamentoAssinaturas gerenciamentoAssinaturas;
    private GerenciamentoClientes gerenciamentoClientes;
    private GerenciamentoAplicativos gerenciamentoAplicativos;

    public GerenciamentoCobranca(GerenciamentoAssinaturas gerenciamentoAssinaturas,
        GerenciamentoClientes gerenciamentoClientes,
        GerenciamentoAplicativos gerenciamentoAplicativos) {
        this.gerenciamentoAssinaturas = gerenciamentoAssinaturas;
        this.gerenciamentoClientes = gerenciamentoClientes;
        this.gerenciamentoAplicativos = gerenciamentoAplicativos;
    }

    public List<Cobranca> gerarListaCobranca() {
        List<Assinatura> assinaturas = gerenciamentoAssinaturas.listar();
        List<Cobranca> cobrancas = new ArrayList<>();
        Map<String, Double> cobrancasMap = new HashMap<>();

        for (Assinatura assinatura : assinaturas) {
            if (assinatura.getMesAnoDeFimVigencia() == null) {
                String codigoApp = assinatura.getCodigoApp();
                double valMensal = gerenciamentoAplicativos.obterValorMensal(codigoApp);
                String cpfCliente = assinatura.getCpfCliente();
                cobrancasMap.put(cpfCliente, cobrancasMap.getOrDefault(cpfCliente, 0.0) + valMensal);
            }
        }
        for (Map.Entry<String, Double> entry : cobrancasMap.entrySet()) {
            String cpfCliente = entry.getKey();
            double valorCobrado = entry.getValue();
            System.out.println("Cobrança para CPF " + cpfCliente + ": " + valorCobrado);
            Cliente cliente = gerenciamentoClientes.buscarId(cpfCliente);
        if (cliente != null) {
            cobrancas.add(new Cobranca(cliente.getNome(), cliente.getEmail(), valorCobrado));
        } else {
            System.out.println("Cliente não encontrado para CPF " + cpfCliente);
        }
    }
    return cobrancas;
}

    public Map<String, Double> listarFaturamentoPorAplicativo(String nomeApp) {
    Map<String, Double> faturamentoPorSO = new HashMap<>();
    List<Assinatura> assinaturas = gerenciamentoAssinaturas.listar();

    for (Assinatura assinatura : assinaturas) {
        if (assinatura.getMesAnoDeFimVigencia() == null) {
            double valMensal = gerenciamentoAplicativos.obterValorMensal(assinatura.getCodigoApp());
            Aplicativo aplicativo = gerenciamentoAplicativos.buscarId(assinatura.getCodigoApp());
            if (aplicativo != null && aplicativo.getNome().equals(nomeApp)) {
                String so = aplicativo.getSO();
                // Adiciona o valor ao total do SO no mapa
                faturamentoPorSO.put(so, faturamentoPorSO.getOrDefault(so, 0.0) + valMensal);
            }
        }
    }
    return faturamentoPorSO;
}

    private static final String arquivoCobrancas = "cobrancas.txt";

    public void salvarEmArquivo() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(arquivoCobrancas))) {
            outputStream.writeObject(gerarListaCobranca());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Cobranca> carregarDeArquivo() {
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(arquivoCobrancas))) {
        @SuppressWarnings("unchecked")
        List<Cobranca> listaCarregada = (List<Cobranca>) inputStream.readObject();
        return listaCarregada;
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    return new ArrayList<>(); // Ou outro valor padrão, dependendo do seu caso
}
}


    








    


