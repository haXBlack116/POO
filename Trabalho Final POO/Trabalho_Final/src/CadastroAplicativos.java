import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CadastroAplicativos extends JFrame {
    private JTextField codigoTextField;
    private JTextField nomeTextField;
    private JTextField soTextField;
    private JTextField licensaTextField;

    private GerenciamentoAplicativos gerenciamentoAplicativos;

    public CadastroAplicativos(GerenciamentoAplicativos gerenciamentoAplicativos) {
        this.gerenciamentoAplicativos = gerenciamentoAplicativos;
        setTitle("Cadastro de Aplicativo");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel codigoLabel = new JLabel("Código:");
        codigoTextField = new JTextField();
        JLabel nomeLabel = new JLabel("Nome:");
        nomeTextField = new JTextField();
        JLabel soLabel = new JLabel("Sistema Operacional:");
        soTextField = new JTextField();
        JLabel licensaLabel = new JLabel("Valor da Licensa:");
        licensaTextField = new JTextField();
        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarAplicativo();
            }
        });
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela
            }
        });

        panel.add(codigoLabel);
        panel.add(codigoTextField);
        panel.add(nomeLabel);
        panel.add(nomeTextField);
        panel.add(soLabel);
        panel.add(soTextField);
        panel.add(licensaLabel);
        panel.add(licensaTextField);
        panel.add(cadastrarButton);
        panel.add(cancelarButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cadastrarAplicativo() {
        String codigo = codigoTextField.getText();
        String nome = nomeTextField.getText();
        String so = soTextField.getText();
        String licensa = licensaTextField.getText();

        if (!codigo.isEmpty() && !nome.isEmpty() && !so.isEmpty() && !licensa.isEmpty()) {
            Aplicativo novoAplicativo = new Aplicativo(codigo, nome, so, licensa);
            if (gerenciamentoAplicativos.existe(codigo)) {
                gerenciamentoAplicativos.editar(codigo, novoAplicativo);
            } else {
                gerenciamentoAplicativos.cadastrar(novoAplicativo);
            }
            JOptionPane.showMessageDialog(this, "Aplicativo cadastrado/editado com sucesso!");
            dispose(); // Fecha a janela após cadastrar/editar
        } else {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
        }
    }
}


