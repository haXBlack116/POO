import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroClientes extends JFrame {
    private JTextField cpfTextField;
    private JTextField nomeTextField;
    private JTextField emailTextField;

    private GerenciamentoClientes gerenciamentoClientes;

    public CadastroClientes(GerenciamentoClientes gerenciamentoClientes) {
        this.gerenciamentoClientes = gerenciamentoClientes;

        setTitle("Cadastro de Cliente");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel cpfLabel = new JLabel("CPF:");
        cpfTextField = new JTextField();
        JLabel nomeLabel = new JLabel("Nome:");
        nomeTextField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailTextField = new JTextField();

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela
            }
        });
        panel.add(cpfLabel);
        panel.add(cpfTextField);
        panel.add(nomeLabel);
        panel.add(nomeTextField);
        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(cadastrarButton);
        panel.add(cancelarButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cadastrarCliente() {
        String cpf = cpfTextField.getText();
        String nome = nomeTextField.getText();
        String email = emailTextField.getText();

        if (!cpf.isEmpty() && !nome.isEmpty() && !email.isEmpty()) {
            Cliente novoCliente = new Cliente(cpf, nome, email);
            if (gerenciamentoClientes.existe(cpf)) {
                gerenciamentoClientes.editar(cpf, novoCliente);
            } else {
                gerenciamentoClientes.cadastrar(novoCliente);
            }
            JOptionPane.showMessageDialog(this, "Cliente cadastrado/editado com sucesso!");
            dispose(); // Fecha a janela após cadastrar/editar
        } else {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
        }
    }
}

