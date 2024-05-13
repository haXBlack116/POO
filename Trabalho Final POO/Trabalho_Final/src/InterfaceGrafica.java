import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class InterfaceGrafica extends JFrame {
    private JPanel panel;
    private GerenciamentoClientes gerenciamentoClientes = new GerenciamentoClientes();
    private GerenciamentoAplicativos gerenciamentoAplicativos = new GerenciamentoAplicativos();
    private GerenciamentoAssinaturas gerenciamentoAssinaturas = new GerenciamentoAssinaturas();
    private GerenciamentoCobranca gerenciamentoCobranca = new GerenciamentoCobranca(gerenciamentoAssinaturas, gerenciamentoClientes, gerenciamentoAplicativos);

    public InterfaceGrafica() {
        inicializarComponentes();
        configurarBotoes();
        configurarMenu();
        setVisible(true);
    }

    private void inicializarComponentes() {
        setTitle("Gestão de Startups");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel);
    }

    private void configurarBotoes() {
        adicionarBotao("Cadastrar/Editar Cliente", this::abrirCadastroCliente);
        adicionarBotao("Cadastrar/Editar Aplicativo", this::abrirCadastroAplicativo);
        adicionarBotao("Gerenciar Assinaturas", this::abrirGerenciamentoAssinaturas);
        adicionarBotao("Listar Clientes", this::exibirListaClientes);
        adicionarBotao("Listar Aplicativos", this::exibirListaAplicativos);
        adicionarBotao("Listar Assinaturas de um Cliente", this::exibirListaAssinaturas);
        adicionarBotao("Listar Assinantes de um Aplicativo", this::listarAssinantesDeUmAplicativo);
        adicionarBotao("Listar Clientes que devem ser cobrados no mês corrente.", this::gerarListaCobranca);
        adicionarBotao("Listar Faturamento com um determinado aplicativo no mês corrente", this::exibirFaturamentoPorAplicativo);
    }

    private void adicionarBotao(String texto, Runnable action) {
        JButton button = new JButton(texto);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(e -> action.run());
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); 
    }

    private void configurarMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu arquivoMenu = new JMenu("Arquivo");
        menuBar.add(arquivoMenu);
        arquivoMenu.add(criarMenuItem("Salvar", this::salvarDados));
        arquivoMenu.add(criarMenuItem("Abrir", this::carregarDados));
    }

    private JMenuItem criarMenuItem(String texto, Runnable action) {
        JMenuItem item = new JMenuItem(texto);
        item.addActionListener(e -> action.run());
        return item;
    }

    private void salvarDados() {
        gerenciamentoClientes.salvarEmArquivo();
        gerenciamentoAplicativos.salvarEmArquivo();
        gerenciamentoAssinaturas.salvarEmArquivo();
        gerenciamentoCobranca.salvarEmArquivo();
        JOptionPane.showMessageDialog(this, "Dados salvos com sucesso.");
    }


    private void carregarDados() {
        gerenciamentoClientes.carregarDeArquivo();
        gerenciamentoAplicativos.carregarDeArquivo();
        gerenciamentoAssinaturas.carregarDeArquivo();
        gerenciamentoCobranca.carregarDeArquivo();
        JOptionPane.showMessageDialog(this, "Dados carregados com sucesso.");
    }


    private void abrirCadastroCliente() {
        while (true) {
        JPanel panel = new JPanel(new GridLayout(3, 2)); 
        JTextField cpfField = new JTextField(10);
        JTextField nomeField = new JTextField(10);
        JTextField emailField = new JTextField(10);

        panel.add(new JLabel("CPF:"));
        panel.add(cpfField);
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.setPreferredSize(new Dimension(300, 100));
        int result = JOptionPane.showConfirmDialog(null, panel, "Cadastrar/Editar Cliente",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String cpf = cpfField.getText();
            String nome = nomeField.getText();
            String email = emailField.getText();
            if (!cpf.isEmpty() && !nome.isEmpty() && !email.isEmpty()) {
                Cliente clienteExistente = gerenciamentoClientes.buscarId(cpf);
                if (clienteExistente != null) {
                    // Cliente existente, editar
                    Cliente novoCliente = new Cliente(cpf, nome, email);
                    gerenciamentoClientes.editar(cpf, novoCliente);
                    JOptionPane.showMessageDialog(null, "Cliente editado com sucesso!");
                } else {
                    // Cliente não existe, cadastrar
                    Cliente novoCliente = new Cliente(cpf, nome, email);
                    gerenciamentoClientes.cadastrar(novoCliente);
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            }
        } else {
            break; // Sai do loop se o usuário clicar em Cancelar
        }
    }
}

    private void abrirCadastroAplicativo() {
        while (true) {
        JPanel panel = new JPanel(new GridLayout(4, 2)); 
        JTextField codigoField = new JTextField(10);
        JTextField nomeField = new JTextField(10);
        JTextField soField = new JTextField(10);
        JTextField licensaField = new JTextField(10);

        panel.add(new JLabel("Código:"));
        panel.add(codigoField);
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Sistema Operacional:"));
        panel.add(soField);
        panel.add(new JLabel("Valor Mensal da Licença:"));
        panel.add(licensaField);

        panel.setPreferredSize(new Dimension(300, 100));
        int result = JOptionPane.showConfirmDialog(null, panel, "Cadastrar/Editar Aplicativo",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);


        if (result == JOptionPane.OK_OPTION) {
            String codigo = codigoField.getText();
            String nome = nomeField.getText();
            String so = soField.getText();
            String licensa = licensaField.getText();


            if (!codigo.isEmpty() && !nome.isEmpty() && !so.isEmpty() && !licensa.isEmpty()) {
                Aplicativo aplicativoExistente = gerenciamentoAplicativos.buscarId(codigo);

                if (aplicativoExistente != null) {
                    Aplicativo novoAplicativo = new Aplicativo(codigo, nome, so, licensa);
                    gerenciamentoAplicativos.editar(codigo, novoAplicativo);
                    JOptionPane.showMessageDialog(null, "Aplicativo editado com sucesso!");
                } else {
                    Aplicativo novoAplicativo = new Aplicativo(codigo, nome, so, licensa);
                    gerenciamentoAplicativos.cadastrar(novoAplicativo);
                    JOptionPane.showMessageDialog(null, "Aplicativo cadastrado com sucesso!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            }
        } else {
            break; // Sai do loop se o usuário clicar em Cancelar
        }
    }
}

    private void abrirGerenciamentoAssinaturas() {
        int escolha = JOptionPane.showOptionDialog(
        null,
        "Escolha uma opção:",
        "Gerenciar Assinaturas",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE,
        null,
        new Object[]{"Registrar Assinatura", "Cancelar Assinatura", "Cancelar"},
        null
);
        if (escolha == 0) {
            registrarAssinatura();
    }   else if (escolha == 1) {
            cancelarAssinatura();
    }
}

    private void registrarAssinatura() {
        JTextField cpfField = new JTextField(10);
        JTextField codigoAssinaturaField = new JTextField(10);
        JTextField codigoAppField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("CPF do Cliente:"));
        panel.add(cpfField);
        panel.add(new JLabel("Código da Assinatura:"));
        panel.add(codigoAssinaturaField);
        panel.add(new JLabel("Código do Aplicativo:"));
        panel.add(codigoAppField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Registrar Assinatura",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String cpf = cpfField.getText();
            String codigoAssinatura = codigoAssinaturaField.getText();
            String codigoApp = codigoAppField.getText();

        if (!cpf.isEmpty() && !codigoAssinatura.isEmpty() && !codigoApp.isEmpty()) {
            // Verifica se o cliente e o aplicativo existem
            Cliente cliente = gerenciamentoClientes.buscarId(cpf);
            Aplicativo aplicativo = gerenciamentoAplicativos.buscarId(codigoApp);

            if (cliente != null && aplicativo != null) {
            Assinatura novaAssinatura = new Assinatura(cpf, cliente.getNome(), cliente.getEmail(), codigoAssinatura, codigoApp);
            gerenciamentoAssinaturas.cadastrar(novaAssinatura);

            // Adiciona o registro de início de vigência ao cadastrar a assinatura
            gerenciamentoAssinaturas.registrarInicioVigencia(codigoAssinatura);

            // Exibe mensagem de sucesso
            JOptionPane.showMessageDialog(null, "Assinatura registrada com sucesso!");
        } else {
                JOptionPane.showMessageDialog(null, "Cliente ou aplicativo não encontrado!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
        }  
    }
    abrirGerenciamentoAssinaturas();
}

    private void cancelarAssinatura() {
        while (true) {
        JTextField codigoAssinaturaField = new JTextField(10);
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Código da Assinatura:"));
        panel.add(codigoAssinaturaField);

        int result = JOptionPane.showOptionDialog(
                null,
                panel,
                "Cancelar Assinatura",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{"OK", "Cancelar"},
                "OK"
        );
        if (result == JOptionPane.OK_OPTION) {
            String codigoAssinatura = codigoAssinaturaField.getText();
            if (!codigoAssinatura.isEmpty()) {
                Assinatura assinatura = gerenciamentoAssinaturas.buscarId(codigoAssinatura);
                if (assinatura != null) {
                    gerenciamentoAssinaturas.registrarEncerramentoVigencia(codigoAssinatura);
                    JOptionPane.showMessageDialog(null, "Assinatura cancelada com sucesso!\nFim da vigência: " +
                            assinatura.getMesAnoDeFimVigencia());
                    boolean sucessoCancelamento = gerenciamentoAssinaturas.remover(codigoAssinatura);
                    if (sucessoCancelamento) {
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Falha ao remover a assinatura!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Código de assinatura não encontrado!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Preencha o campo!");
            }
        } else {
            abrirGerenciamentoAssinaturas();
            break;
        }
    }
}

    private void exibirListaClientes() {
    List<Cliente> clientes = gerenciamentoClientes.listar();

    if (clientes.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhum Cliente Cadastrado.");
    } else {
        Object[][] data = new Object[clientes.size()][3];
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            data[i][0] = cliente.getCpf();
            data[i][1] = cliente.getNome();
            data[i][2] = cliente.getEmail();
        }
        String[] colunas = {"CPF", "Nome", "Email"};
        DefaultTableModel model = new DefaultTableModel(data, colunas);
       
        JTable table = new JTable(model);
       
        JScrollPane scrollPane = new JScrollPane(table);
       
        scrollPane.setPreferredSize(new Dimension(600, 300));

        JOptionPane.showMessageDialog(
                null,
                scrollPane,
                "Clientes Cadastrados",
                JOptionPane.PLAIN_MESSAGE
        );
    }
}

    private void exibirListaAplicativos() {
    List<Aplicativo> aplicativos = gerenciamentoAplicativos.listar();
    if (aplicativos.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhum Aplicativo Cadastrado.");
    } else {
        Object[][] data = new Object[aplicativos.size()][4];
        for (int i = 0; i < aplicativos.size(); i++) {
            Aplicativo aplicativo = aplicativos.get(i);
            data[i][0] = aplicativo.getCodigo();
            data[i][1] = aplicativo.getNome();
            data[i][2] = aplicativo.getSO();
            data[i][3] = aplicativo.getValMensal();
        }
        String[] colunas = {"Código", "Nome", "Sistema Operacional", "Valor Mensal da Licensa"};

        DefaultTableModel model = new DefaultTableModel(data, colunas);

        JTable table = new JTable(model);
       
        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(600, 300));

        JOptionPane.showMessageDialog(
                null,
                scrollPane,
                "Clientes Cadastrados",
                JOptionPane.PLAIN_MESSAGE
        );
    }
}

    private void exibirListaAssinaturas() {
    String cpfCliente = JOptionPane.showInputDialog(null, "Informe o CPF do Cliente:");
    if (cpfCliente != null && !cpfCliente.isEmpty()) {
        List<Assinatura> assinaturasCliente = gerenciamentoAssinaturas.obterAssinaturasPorCliente(cpfCliente);
        if (!assinaturasCliente.isEmpty()) {
            Object[][] data = new Object[assinaturasCliente.size()][4];
            for (int i = 0; i < assinaturasCliente.size(); i++) {
                Assinatura assinatura = assinaturasCliente.get(i);
                data[i][0] = assinatura.getCpf();
                data[i][1] = assinatura.getCodigoAssinatura();
                data[i][2] = assinatura.getCodigoApp();
                data[i][3] = assinatura.getMesAnoDeInicioVigencia();
            }
            String[] colunas = {"CPF do Cliente", "Código da Assinatura", "Código do Aplicativo", "Início da Vigência"};

            DefaultTableModel model = new DefaultTableModel(data, colunas);

            JTable table = new JTable(model);

            JScrollPane scrollPane = new JScrollPane(table);

            scrollPane.setPreferredSize(new Dimension(600, 300));
           
            JOptionPane.showMessageDialog(
                    null,
                    scrollPane,
                    "Assinaturas do Cliente: " + cpfCliente,
                    JOptionPane.PLAIN_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma assinatura cadastrada para o CPF informado.");
        }
    }
}

    private void listarAssinantesDeUmAplicativo() {
    String nomeApp = JOptionPane.showInputDialog("Digite o Nome do aplicativo:");
    if (nomeApp != null && !nomeApp.isEmpty()) {
        List<Assinatura> assinantes = gerenciamentoAssinaturas.listarAssinantesDoAppPorNome(nomeApp, gerenciamentoAplicativos);
        if (!assinantes.isEmpty()) {
            String[] colunas = {"CPF", "Nome", "E-mail", "Código Assinatura", "Código Aplicativo"};
            DefaultTableModel model = new DefaultTableModel(colunas, 0);
            for (Assinatura assinatura : assinantes) {
                Cliente cliente = gerenciamentoClientes.buscarId(assinatura.getCpf());
                if (cliente != null) {
                    Object[] rowData = {cliente.getCpf(), cliente.getNome(), cliente.getEmail(), assinatura.getCodigoAssinatura(), assinatura.getCodigoApp()};
                    model.addRow(rowData);
                }
            }
            JTable table = new JTable(model);

            JScrollPane scrollPane = new JScrollPane(table);

            scrollPane.setPreferredSize(new Dimension(600, 300));

            JOptionPane.showMessageDialog(
                    null,
                    scrollPane,
                    "Assinantes do Aplicativo: " + nomeApp,
                    JOptionPane.PLAIN_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum assinante encontrado para o aplicativo: " + nomeApp);
        }
    }
}

    private void gerarListaCobranca() {
    List<Cobranca> listaCobranca = gerenciamentoCobranca.gerarListaCobranca();
    if (!listaCobranca.isEmpty()) {
        String[] colunas = {"Nome", "E-mail", "Valor a ser Cobrado"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        for (Cobranca cobranca : listaCobranca) {
            Object[] rowData = {cobranca.getNomeCliente(), cobranca.getEmailCliente(), cobranca.getValorCobrado() + "R$"};
            model.addRow(rowData);
        }
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(600, 300));

        JOptionPane.showMessageDialog(
                null,
                scrollPane,
                "Lista de Cobrança",
                JOptionPane.PLAIN_MESSAGE
        );
    } else {
        JOptionPane.showMessageDialog(null, "Nenhum cliente para cobrança no mês corrente.");
    }
}

    private void exibirFaturamentoPorAplicativo() {
    String nomeApp = JOptionPane.showInputDialog("Digite o nome do aplicativo:");
    if (nomeApp != null && !nomeApp.isEmpty()) {
        Map<String, Double> faturamentoPorSO = gerenciamentoCobranca.listarFaturamentoPorAplicativo(nomeApp);
        if (!faturamentoPorSO.isEmpty()) {
            double faturamentoTotal = faturamentoPorSO.values().stream().mapToDouble(Double::doubleValue).sum();
            StringBuilder message = new StringBuilder("Faturamento Total: " + faturamentoTotal + " " + "R$\n");
            message.append("Faturamento por Sistema Operacional:\n");
            for (Map.Entry<String, Double> entry : faturamentoPorSO.entrySet()) {
                message.append(entry.getKey()).append(": ").append(entry.getValue()).append(" " + "R$" + "  ");
            }
            JOptionPane.showMessageDialog(null, message.toString(), "Faturamento por Aplicativo", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum faturamento registrado para o aplicativo: " + nomeApp);
        }
    }
}
}

