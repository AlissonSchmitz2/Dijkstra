package br.com.dijkstra.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TelaConfiguracaoWindow extends JFrame {

	private static final long serialVersionUID = -6412279950219631238L;

	private JTextField txtPasta, txtSucesso, txtErro;
	private JButton btnSalvar, btnSeleciona;
	private JCheckBox ckbAleatorio;
	private JLabel saida;

	public TelaConfiguracaoWindow() {
		
		setTitle("Configuração");
	
		setSize(300, 220);
		setLayout(null);
		
		setResizable(false);
		setMaximumSize(null);
		setLocationRelativeTo(null);
		criarComponentes();
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Image imagemTitulo = Toolkit.getDefaultToolkit().getImage("br/com/dijkstra/icons/config2.png");
		this.setIconImage(imagemTitulo);
	
	}

	public void criarComponentes() {
	
		saida = new JLabel("Pasta: ");
		saida.setBounds(15, 10, 200, 25);
		getContentPane().add(saida);

		btnSeleciona = new JButton();
		btnSeleciona.setBounds(260, 10, 20, 25);
		getContentPane().add(btnSeleciona);
		
		txtPasta = new JTextField();
		txtPasta.setBounds(80, 10, 180, 25);
		txtPasta.setToolTipText("Pasta: ");
		getContentPane().add(txtPasta);
		
		btnSeleciona.setAction(new AbstractAction("...") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtPasta.setText(fileChooser());
			}
		});

		saida = new JLabel("Sucesso: ");
		saida.setBounds(15, 45, 200, 25);
		getContentPane().add(saida);

		txtSucesso = new JTextField();
		txtSucesso.setBounds(80, 45, 100, 25);
		txtSucesso.setToolTipText("Sucesso: ");
		txtSucesso.setEditable(false);
		txtSucesso.setBackground(Color.white);
		getContentPane().add(txtSucesso);
		
		saida = new JLabel("Erro: ");
		saida.setBounds(15, 80, 200, 25);
		getContentPane().add(saida);

		txtErro = new JTextField();
		txtErro.setBounds(80, 80, 100, 25);
		txtErro.setToolTipText("Erro: ");
		txtErro.setEditable(false);
		txtErro.setBackground(Color.white);
		getContentPane().add(txtErro);

		// CheckBox
		ckbAleatorio = new JCheckBox();
		ckbAleatorio.setBounds(80, 110, 20, 25);
		ckbAleatorio.setToolTipText("Rota automática: ");
		getContentPane().add(ckbAleatorio);

		saida = new JLabel("Rota automática ");
		saida.setBounds(100, 110, 200, 25);
		getContentPane().add(saida);
		
		btnSalvar = new JButton("SALVAR");
		btnSalvar.setBounds(80, 140, 100, 25);
		getContentPane().add(btnSalvar);
		
	}
	
	private String fileChooser() {
		
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);

	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	      return chooser.getSelectedFile().getPath().toString();
	    }

	    return "Selecione o arquivo";
	}

}
