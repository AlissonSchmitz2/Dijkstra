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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.dijkstra.lib.ManipularArquivo;
import br.com.dijkstra.model.Config;

public class TelaConfiguracaoWindow extends JFrame {

	private static final long serialVersionUID = -6412279950219631238L;

	private JTextField txtPasta, txtSucesso, txtErro;
	private JButton btnSalvar, btnSelecionaPasta, btnSelecionaErro, btnSelecionaSucesso;
	private JCheckBox ckbAleatorio;
	private JLabel saida;
	
	private Config config;
	private ManipularArquivo aM = new ManipularArquivo();
	
	public TelaConfiguracaoWindow() {
		setTitle("Configuração");
		this.config = new Config();
		
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

		btnSelecionaPasta = new JButton();
		btnSelecionaPasta.setBounds(262, 10, 20, 25);
		btnSelecionaPasta.setToolTipText("Clique aqui para selecionar o caminho");
		getContentPane().add(btnSelecionaPasta);
		
		txtPasta = new JTextField();
		txtPasta.setBounds(80, 10, 180, 25);
		txtPasta.setToolTipText("Pasta: ");
		getContentPane().add(txtPasta);
		
		btnSelecionaPasta.setAction(new AbstractAction("...") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtPasta.setText(fileChooser());
			}
		});

		saida = new JLabel("Sucesso: ");
		saida.setBounds(15, 45, 200, 25);
		getContentPane().add(saida);
		
		btnSelecionaSucesso = new JButton();
		btnSelecionaSucesso.setBounds(183, 45, 20, 25);
		btnSelecionaSucesso.setToolTipText("Clique aqui para selecionar o caminho");
		getContentPane().add(btnSelecionaSucesso);

		txtSucesso = new JTextField();
		txtSucesso.setBounds(80, 45, 100, 25);
		txtSucesso.setToolTipText("Sucesso: ");
		txtSucesso.setBackground(Color.white);
		getContentPane().add(txtSucesso);
		
		btnSelecionaSucesso.setAction(new AbstractAction("...") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtSucesso.setText(fileChooser());
			}
		});
		
		saida = new JLabel("Erro: ");
		saida.setBounds(15, 80, 200, 25);
		getContentPane().add(saida);
		
		btnSelecionaErro = new JButton();
		btnSelecionaErro.setBounds(183, 80, 20, 25);
		getContentPane().add(btnSelecionaErro);

		txtErro = new JTextField();
		txtErro.setBounds(80, 80, 100, 25);
		txtErro.setToolTipText("Erro: ");
		txtErro.setBackground(Color.white);
		getContentPane().add(txtErro);

		btnSelecionaErro.setAction(new AbstractAction("...") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtErro.setText(fileChooser());
			}
		});
		
		// CheckBox
		ckbAleatorio = new JCheckBox();
		ckbAleatorio.setBounds(80, 110, 20, 25);
		ckbAleatorio.setToolTipText("Rota automática: ");
		getContentPane().add(ckbAleatorio);

		saida = new JLabel("Rota automática ");
		saida.setBounds(100, 110, 200, 25);
		getContentPane().add(saida);
		
		btnSalvar = new JButton();
		btnSalvar.setBounds(80, 140, 100, 25);
		getContentPane().add(btnSalvar);


		btnSalvar.setAction(new AbstractAction("SALVAR") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				salvarDados();
				aM.inserirDado(config);
				JOptionPane.showMessageDialog(null, "Configuração salva com sucesso!");
				setVisible(false);
			}
		});
		
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

	private void salvarDados(){
		
		config.setCaminhoPasta(txtPasta.getText());
		config.setCaminhoSucesso(txtSucesso.getText());
		config.setCaminhoErro(txtErro.getText());
		config.setCheck(ckbAleatorio.isSelected());
	}
	
}
