package br.com.dijkstra.view;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

import br.com.dijkstra.lib.ManipularArquivo;
import br.com.dijkstra.model.CaminhoManual;
import br.com.dijkstra.model.CaminhoManualTableModel;

public class TelaBuscaWindow extends JFrame {		
	private static final long serialVersionUID = 8509005971460712336L;
	
	private JTextField textBusca, textCodOrigem, textCidadeOrigem, textCodDestino, 
	textCidadeDestino, textKm ;
	private JButton btnBuscar, btnAdd, btnSalvar, btnProcessar, btnLimpar;
	private JLabel label;
	
	private JScrollPane scrollpane;
	private TableModel tableModel = new CaminhoManualTableModel();
	private ArrayList<CaminhoManual> listCM = new ArrayList<>();
	ManipularArquivo mA = new ManipularArquivo();
	private boolean importouTXT = false;
	
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				if(textBusca.isFocusOwner()) {
					btnBuscar.doClick();
				} else if( textCidadeDestino.isFocusOwner() || textCodDestino.isFocusOwner() || 
						  textCidadeOrigem.isFocusOwner() || textCodOrigem.isFocusOwner() || 
						  textKm.isFocusOwner() ) {
					btnAdd.doClick();
				}
			}
		}
	};

		public TelaBuscaWindow() {
			setTitle("Dijsktra - Busca por melhor caminho");
			setSize(635,525);
			setResizable(false);
			setLayout(null);
			
			setLocationRelativeTo(null);
			criarComponentes();
			setVisible(true);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			Image imagemTitulo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/busca.png"));
			this.setIconImage(imagemTitulo);
			
		}
		
		public void criarComponentes() {
			
			label = new JLabel("Buscar");
			label.setBounds(10, 0, 50, 45);
			getContentPane().add(label);
			
			textBusca = new JTextField();
			textBusca.setBounds(70, 10, 300, 25);
			getContentPane().add(textBusca);
			textBusca.addKeyListener(acao);

			//Campos de ORIGEM
			label = new JLabel("C�digo: ");
			label.setBounds(10, 40, 50, 45);
			getContentPane().add(label);	
					
			textCodOrigem = new JTextField();
			textCodOrigem.setBounds(70, 50, 70, 25);
			getContentPane().add(textCodOrigem);
			textCodOrigem.addKeyListener(acao);
					
			label = new JLabel("Cidade: ");
			label.setBounds(160, 40, 50, 45);
			getContentPane().add(label);	
					
			textCidadeOrigem = new JTextField();
			textCidadeOrigem.setBounds(220, 50, 150, 25);
			getContentPane().add(textCidadeOrigem);
			textCidadeOrigem.addKeyListener(acao);
			
			label = new JLabel("(ORIGEM)");
			label.setBounds(375, 40, 100, 45);
			getContentPane().add(label);
					
			//Campos de DESTINO
			label = new JLabel("C�digo: ");
			label.setBounds(10, 70, 50, 45);
			getContentPane().add(label);
						
			textCodDestino = new JTextField();
			textCodDestino.setBounds(70, 80, 70, 25);
			getContentPane().add(textCodDestino);
			textCodDestino.addKeyListener(acao);
					
			label = new JLabel("Cidade: ");
			label.setBounds(160, 70, 50, 45);
			getContentPane().add(label);	
			
			textCidadeDestino = new JTextField();
			textCidadeDestino.setBounds(220, 80, 150, 25);
			getContentPane().add(textCidadeDestino);
			textCidadeDestino.addKeyListener(acao);
			
			label = new JLabel("(DESTINO)");
			label.setBounds(375, 70, 100, 45);
			getContentPane().add(label);
			
			label = new JLabel("KM: ");
			label.setBounds(30, 100, 100, 45);
			getContentPane().add(label);
			
			textKm = new JTextField();
			textKm.setBounds(70, 110, 50, 25);
			getContentPane().add(textKm);	
			textKm.addKeyListener(acao);
							
			//Tabela de dados.				
			JTable tabela = new JTable(); 
			tabela.setModel(tableModel);
			//Scroll para tabela.
			scrollpane = new JScrollPane(tabela);
			scrollpane.setBounds(10, 150, 600, 300);
			scrollpane.setVisible(true);
			add(scrollpane); 
							
							
			btnAdd = new JButton(new AbstractAction("+") {	
				private static final long serialVersionUID = 2349363006048570163L;

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					String validacao = validacoesAdicao();
					
					if(validacao.equals("")) {
					CaminhoManual CM = new CaminhoManual();
					CM.setCidadeDestino(textCidadeDestino.getText());
					CM.setCidadeOrigem(textCidadeOrigem.getText());
					CM.setCodigoDestino(Integer.parseInt(textCodDestino.getText()));
					CM.setCodigoOrigem(Integer.parseInt(textCodOrigem.getText()));
					CM.setDistanciaKM(Float.parseFloat(textKm.getText()));
					
					//Adi��o do objeto na lista para depois adicionar no TXT.
					listCM.add(CM);
					
					((CaminhoManualTableModel) tableModel).addRow(CM);	
					
					limparCamposAdicao();
					textCodOrigem.requestFocus();
					
					} else {
						JOptionPane.showMessageDialog(null, "" + validacao);
					}								
				}
				});
			
			btnAdd.setBounds(559, 120, 50, 25);
			getContentPane().add(btnAdd);
			btnAdd.addKeyListener(acao);
			
			btnBuscar = new JButton(new AbstractAction("Buscar") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {					
					
					if("".equals(textBusca.getText())) {					
					textBusca.setText(fileChooser());
					}
					
					try {
						
						if(textBusca.getText().equals("") == false) {
							importouTXT = true;
							listCM.clear();
							ArrayList<CaminhoManual> dados = new ArrayList<>();
								
							dados = mA.recuperarDados(textBusca.getText());
							((CaminhoManualTableModel) tableModel).limpar();
								
							for(int i = 0; i < dados.size(); i++) {							
								((CaminhoManualTableModel) tableModel).addRow(dados.get(i));
							}
							
						} 
						
					} catch (Exception e1) {
						
						JOptionPane.showMessageDialog(rootPane, "O arquivo n�o foi encontrado ou � inv�lido.", "", JOptionPane.ERROR_MESSAGE, null);
					}
					
				}
			});
			btnBuscar.setBounds(375, 10, 100, 25);
			getContentPane().add(btnBuscar);
			btnBuscar.addKeyListener(acao);
			
			btnLimpar = new JButton(new AbstractAction("Limpar") {
				private static final long serialVersionUID = -8959816262699821865L;

				@Override
				public void actionPerformed(ActionEvent arg0) {

					int limparBusca = JOptionPane.showConfirmDialog(null, "Deseja limpar o campo de busca?");
					if(limparBusca == 0) {
						limparCampoBusca();
					}
					
				}
			});
			btnLimpar.setToolTipText("Limpar campo de busca");
			btnLimpar.setBounds(500, 10, 100, 25);
			getContentPane().add(btnLimpar);
			
			btnSalvar = new JButton(new AbstractAction("SALVAR") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(listCM.isEmpty()) {
						JOptionPane.showMessageDialog(rootPane, "N�o h� nada para ser salvo ou nenhuma mudan�a foi realizada no arquivo!", "", JOptionPane.ERROR_MESSAGE, null);					
					} else {
					
					String nomeArquivo;
					
					//Salva no mesmo arquivo oque foi adicionado ap�s a importa��o.
					if(importouTXT) {
						
						nomeArquivo = textBusca.getText();
						mA.inserirDado(listCM, nomeArquivo, true);	
						JOptionPane.showMessageDialog(null, "Arquivo salvo com sucesso!");
						listCM.clear();
						
						int limpar = JOptionPane.showConfirmDialog(null, "Deseja limpar a tabela?");
						
						if(limpar == 0) {
							((CaminhoManualTableModel) tableModel).limpar();
						}
						
					} else {
					
						nomeArquivo = JOptionPane.showInputDialog(null, "Digite o nome do arquivo a ser salvo:");					
						
						if(mA.verificarNomeTxt(nomeArquivo + ".txt")) {		
							
							do {										
								JOptionPane.showMessageDialog(rootPane, "Esse nome j� existe no diret�rio. Por favor, digite outro nome.", "", JOptionPane.ERROR_MESSAGE, null);
								nomeArquivo = JOptionPane.showInputDialog(null, "Digite o nome do arquivo a ser salvo:");
							} while (mA.verificarNomeTxt(nomeArquivo + ".txt"));
							
						}
						
						if(nomeArquivo != null) {
							mA.inserirDado(listCM, "\\" + nomeArquivo + ".txt", false);	
							JOptionPane.showMessageDialog(null, "Arquivo salvo com sucesso!");
							
							int limpar = JOptionPane.showConfirmDialog(null, "Deseja limpar a tabela?");
							
							if(limpar == 0) {
								((CaminhoManualTableModel) tableModel).limpar();
								}
							}
						}
					
					}
				}
			});
			btnSalvar.setBounds(385, 455, 110, 25);
			getContentPane().add(btnSalvar);
						
			btnProcessar = new JButton("PROCESSAR");
			btnProcessar.setBounds(500, 455, 110, 25);
			getContentPane().add(btnProcessar);

		}
		
		private String fileChooser() {			
			
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(null);

		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		      return chooser.getSelectedFile().getPath().toString();
		    }

		    return "";
		}
		
		private String validacoesAdicao() {
			
			if("".equals(textCidadeDestino.getText()) || "".equals(textCidadeOrigem.getText()) || "".equals(textCodDestino.getText())
			   || "".equals(textCodOrigem.getText()) || "".equals(textKm.getText())) {
				return "Todos os campos devem ser preenchidos!";
			} else if(textCodDestino.getText().equals(textCodOrigem.getText())) {
				return "O c�digo de destino n�o pode ser igual ao c�digo de origem!";
			} else if(textCidadeDestino.getText().equals(textCidadeOrigem.getText())) {
				return "O nome da cidade de destino n�o pode ser igual ao da cidade de origem!";
			} else if(verificarLetras(textCodOrigem.getText()) || verificarLetras(textCodDestino.getText()) || verificarLetras(textKm.getText())) {
				return "Os campos 'C�digo' e 'KM' n�o podem conter letras!";
			} else if(textKm.getText().contains(",") || textCodDestino.getText().contains(",") || textCodOrigem.getText().contains(",")) {
				return "Os campos 'C�digo' e 'KM' n�o podem conter v�rgulas";
			} else if(textCodDestino.getText().contains(".") || textCodOrigem.getText().contains(".")) {
				return "Os campos 'C�digo' n�o podem conter pontos";
			}
			
			return "";
		}
		
		//Verifica se uma determinada string possui letras.
		private boolean verificarLetras(String texto) {
			
			for(int i = 0; i < texto.length(); i++) {
				if(Character.isLetter(texto.charAt(i))) {
					return true;
				}
			}
			
			return false;
		}
		
		private void limparCamposAdicao() {
			textCidadeDestino.setText("");
			textCidadeOrigem.setText("");
			textCodDestino.setText("");
			textCodOrigem.setText("");
			textKm.setText("");
		}
		
		private void limparCampoBusca() {
			textBusca.setText("");
		}
		
		@Override
		public void setFocusable(boolean focusable) {	
			super.setFocusable(focusable);
			textBusca.requestFocus();
		}
		
}
