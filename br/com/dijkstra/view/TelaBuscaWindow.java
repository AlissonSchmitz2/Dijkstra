package br.com.dijkstra.view;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
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
	private JButton btnBuscar, btnAdd, btnSalvar, btnProcessar;
	private JLabel label;
	
	private JScrollPane scrollpane;
	private TableModel tableModel = new CaminhoManualTableModel();
	private ArrayList<CaminhoManual> listCM = new ArrayList<>();
	ManipularArquivo mA = new ManipularArquivo();
	private boolean importouTXT = false;

		public TelaBuscaWindow() {
			setTitle("Dijsktra - Busca por melhor caminho");
			setSize(635,525);
			setResizable(false);
			setLayout(null);
			
			setLocationRelativeTo(null);
			criarComponentes();
			setVisible(true);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);

			Image imagemTitulo = Toolkit.getDefaultToolkit().getImage("br/com/dijkstra/icons/busca.png");
			this.setIconImage(imagemTitulo);

		}
		
		public void criarComponentes() {
			
			label = new JLabel("Buscar");
			label.setBounds(10, 0, 50, 45);
			getContentPane().add(label);
			
			textBusca = new JTextField();
			textBusca.setBounds(70, 10, 300, 25);
			getContentPane().add(textBusca);

			//Campos de ORIGEM
			label = new JLabel("Código: ");
			label.setBounds(10, 40, 50, 45);
			getContentPane().add(label);	
					
			textCodOrigem = new JTextField();
			textCodOrigem.setBounds(70, 50, 70, 25);
			getContentPane().add(textCodOrigem);
					
			label = new JLabel("Cidade: ");
			label.setBounds(160, 40, 50, 45);
			getContentPane().add(label);	
					
			textCidadeOrigem = new JTextField();
			textCidadeOrigem.setBounds(220, 50, 150, 25);
			getContentPane().add(textCidadeOrigem);
			
			label = new JLabel("(ORIGEM)");
			label.setBounds(375, 40, 100, 45);
			getContentPane().add(label);
					
			//Campos de DESTINO
			label = new JLabel("Código: ");
			label.setBounds(10, 70, 50, 45);
			getContentPane().add(label);	
						
			textCodDestino = new JTextField();
			textCodDestino.setBounds(70, 80, 70, 25);
			getContentPane().add(textCodDestino);
					
			label = new JLabel("Cidade: ");
			label.setBounds(160, 70, 50, 45);
			getContentPane().add(label);	
			
			textCidadeDestino = new JTextField();
			textCidadeDestino.setBounds(220, 80, 150, 25);
			getContentPane().add(textCidadeDestino);
			
			label = new JLabel("(DESTINO)");
			label.setBounds(375, 70, 100, 45);
			getContentPane().add(label);
			
			label = new JLabel("KM: ");
			label.setBounds(30, 100, 100, 45);
			getContentPane().add(label);
			
			textKm = new JTextField();
			textKm.setBounds(70, 110, 50, 25);
			getContentPane().add(textKm);			
							
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
					
					//Adição do objeto na lista para depois adicionar no TXT.
					listCM.add(CM);
					
					((CaminhoManualTableModel) tableModel).addRow(CM);	
					
					limparCamposAdicao();
					
					} else {
						JOptionPane.showMessageDialog(null, "" + validacao);
					}								
				}
				});
			
			btnAdd.setBounds(559, 120, 50, 25);
			getContentPane().add(btnAdd);
			
			btnBuscar = new JButton(new AbstractAction("Buscar") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {					
					textBusca.setText(fileChooser());					
					
					if(textBusca.getText().equals("Selecione o arquivo") == false) {
					importouTXT = true;
					listCM.clear();
					ArrayList<CaminhoManual> dados = new ArrayList<>();
						
					dados = mA.recuperarDados(textBusca.getText());
					((CaminhoManualTableModel) tableModel).limpar();
						
					for(int i = 0; i < dados.size(); i++) {							
						((CaminhoManualTableModel) tableModel).addRow(dados.get(i));
					}
					}
					 
				}
			});
			btnBuscar.setBounds(375, 10, 100, 25);
			getContentPane().add(btnBuscar);
						
			btnSalvar = new JButton(new AbstractAction("SALVAR") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(listCM.isEmpty()) {
						JOptionPane.showMessageDialog(rootPane, "Não há nada para ser salvo ou nenhuma mudança foi realizada no arquivo!", "", JOptionPane.ERROR_MESSAGE, null);					
					} else {
					
					String nomeArquivo;
					
					//Salva no mesmo arquivo oque foi adicionado após a importação.
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
								JOptionPane.showMessageDialog(rootPane, "Esse nome já existe no diretório. Por favor, digite outro nome.", "", JOptionPane.ERROR_MESSAGE, null);
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

		    return "Selecione o arquivo";
		}
		
		private String validacoesAdicao() {
			
			if("".equals(textCidadeDestino.getText()) || "".equals(textCidadeOrigem.getText()) || "".equals(textCodDestino.getText())
			   || "".equals(textCodOrigem.getText()) || "".equals(textKm.getText())) {
				return "Todos os campos devem ser preenchidos!";
			} else if(textCodDestino.getText().equals(textCodOrigem.getText())) {
				return "O código de destino não pode ser igual ao código de origem!";
			} else if(textCidadeDestino.getText().equals(textCidadeOrigem.getText())) {
				return "O nome da cidade de destino não pode ser igual ao da cidade de origem!";
			}
			
			return "";
		}
		
		private void limparCamposAdicao() {
			textCidadeDestino.setText("");
			textCidadeOrigem.setText("");
			textCodDestino.setText("");
			textCodOrigem.setText("");
			textKm.setText("");
		}
		
		@Override
		public void setFocusable(boolean focusable) {	
			super.setFocusable(focusable);
			textBusca.requestFocus();
		}
		
}
