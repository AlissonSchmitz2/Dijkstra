package br.com.dijkstra.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import br.com.dijkstra.lib.ManipularArquivo;
import br.com.dijkstra.model.Config;

public class TelaConfiguracaoWindow extends JFrame {

	private static final long serialVersionUID = -6412279950219631238L;

	private JTextField txtPasta, txtSucesso, txtErro;
	private JButton btnSalvar, btnSelecionaPasta, btnSelecionaErro, btnSelecionaSucesso;
	private JCheckBox ckbAleatorio;
	private JLabel saida;
	
	private Config config;
	private ManipularArquivo aM;

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
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Image imagemTitulo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/dijkstra/icons/config2.png"));
		this.setIconImage(imagemTitulo);
		
		File diretorio = new File(System.getProperty("user.home") + "\\dijkstra\\data");
		
		if(diretorio.exists()) {
			aM = new ManipularArquivo();
			try{
				config = aM.recuperarDadosConfig(System.getProperty("user.home") + "\\dijkstra\\data\\config.txt");
			}catch(Exception e) {
				e.printStackTrace();
			}
			setarValores(config);
		}
		
		//Verifica se a configuração foi realizada e não deixa fechar a janela sem fazer.
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				File diretorio = new File(System.getProperty("user.home") + "\\dijkstra\\data\\config.txt");
				if(!diretorio.exists()) {
				//TODO: REABRIR JANELA
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
					JOptionPane.showMessageDialog(rootPane, "É necessário realizar as configurações!", null, JOptionPane.ERROR_MESSAGE, null);
				} else {
					setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				}
			}
		});
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
				txtPasta.setText(DirectoryChooser());
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
				txtSucesso.setText(DirectoryChooser());
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
				txtErro.setText(DirectoryChooser());
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
				
				if(validarCampos()) {
					JOptionPane.showMessageDialog(rootPane, "Preencha todos os campos!", null, JOptionPane.ERROR_MESSAGE, null);
					return;
				}
				
				if(diretorioVerificar()) {
					JOptionPane.showMessageDialog(rootPane, "Um ou mais diretórios informados são inválidos!", null, JOptionPane.ERROR_MESSAGE, null);
					return;
				}
				
				salvarDados();
				aM = new ManipularArquivo();
				aM.inserirDado(config);
				new ThreadPrincipal();
				JOptionPane.showMessageDialog(null, "Configuração salva com sucesso!");
				dispose();
			}
		});
		
	}
	
	private String DirectoryChooser() {
		
		JFileChooser chooser = new JFileChooser();
		
		// restringe a amostra a diretórios apenas
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		      return chooser.getSelectedFile().getPath().toString();
		    } 
	    
	    return "Selecione o diretório";
	}

	private void salvarDados(){
		
		config.setCaminhoPasta(txtPasta.getText());
		config.setCaminhoSucesso(txtSucesso.getText());
		config.setCaminhoErro(txtErro.getText());
		config.setCheck(ckbAleatorio.isSelected());
	}
	
	public void setarValores(Config config) {
		txtPasta.setText(config.getCaminhoPasta());
		txtSucesso.setText(config.getCaminhoSucesso());
		txtErro.setText(config.getCaminhoErro());
		ckbAleatorio.setSelected(config.getCheck());
	}
	
	@Override
	public void setFocusable(boolean focusable) {	
		super.setFocusable(focusable);
		txtPasta.requestFocus();
	}
	
	public boolean validarCampos(){
		if(txtPasta.getText().isEmpty()
		|| txtErro.getText().isEmpty()
		|| txtSucesso.getText().isEmpty()){
			return true;
		}
		
		return false;
		
	}
	
	public boolean validarDiretorios(){
		
		if(txtPasta.getText().isEmpty()
		|| txtErro.getText().isEmpty()
		|| txtSucesso.getText().isEmpty()){
			return true;
		}
		
		return false;
		
	}
	
	 private boolean diretorioVerificar() {

		 File diretorio1 = new File(txtPasta.getText());
		 File diretorio2 = new File(txtErro.getText());
		 File diretorio3 = new File(txtSucesso.getText());
	    	
	    	if(!diretorio1.exists() ||
	    	   !diretorio2.exists() ||
	    	   !diretorio3.exists()) {
	    		return true;
	    	}
	    	
	    	return false;
	    }
	 
}
