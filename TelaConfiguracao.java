import java.awt.event.ActionEvent;

import javax.sound.midi.Soundbank;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TelaConfiguracao extends JFrame {

	private JTextField txtPasta, txtSucesso, txtErro;
	private JButton btnSalvar;
	private JCheckBox ckbAleatorio;
	private JLabel saida;

	public TelaConfiguracao() {
		
		
		setTitle("Configuração");
		setSize(635, 525);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);

		setLocationRelativeTo(null);
		criarComponentes();
		setVisible(true);
	}

	public void criarComponentes() {
		
		
		setSize(300, 220);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);

		saida = new JLabel("Pasta: ");
		saida.setBounds(15, 10, 200, 25);
		getContentPane().add(saida);

		txtPasta = new JTextField();
		txtPasta.setBounds(80, 10, 200, 25);
		txtPasta.setToolTipText("Pasta: ");
		getContentPane().add(txtPasta);

		saida = new JLabel("Sucesso: ");
		saida.setBounds(15, 45, 200, 25);
		getContentPane().add(saida);

		txtSucesso = new JTextField();
		txtSucesso.setBounds(80, 45, 100, 25);
		txtSucesso.setToolTipText("Sucesso: ");
		getContentPane().add(txtSucesso);

		saida = new JLabel("Erro: ");
		saida.setBounds(15, 80, 200, 25);
		getContentPane().add(saida);

		txtErro = new JTextField();
		txtErro.setBounds(80, 80, 100, 25);
		txtErro.setToolTipText("Erro: ");
		getContentPane().add(txtErro);

		// CheckBox
		ckbAleatorio = new JCheckBox();
		ckbAleatorio.setBounds(80, 110, 20, 25);
		ckbAleatorio.setToolTipText("Rota automática: ");
		getContentPane().add(ckbAleatorio);

		saida = new JLabel("Rota automática ");
		saida.setBounds(100, 110, 200, 25);
		getContentPane().add(saida);

		btnSalvar = new JButton(new AbstractAction("SALVAR") {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnSalvar.setBounds(80, 140, 100, 25);
		getContentPane().add(btnSalvar);
	}

	public static void main(String[] args) {
		
		new TelaConfiguracao();
	}
}
