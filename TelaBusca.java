
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class TelaBusca extends JFrame {
		
	private static final long serialVersionUID = 8509005971460712336L;
	private JTextField textBusca, textCodOrigem, textCidadeOrigem, textCodDestino, 
	textCidadeDestino, textKm ;
	private JButton btnBuscar, btnAdd, btnSalvar, btnProcessar;
	private JLabel label;
	
	private JPanel painel;
	private JTable tabela;
	private JScrollPane scrollpane;
		
		public TelaBusca() {
			setTitle("Dijsktra - Busca por melhor caminho");
			setSize(635,525);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLayout(null);
		
			setLocationRelativeTo(null);
			criarComponentes();
			setVisible(true);
		}
		
		public void criarComponentes() {
			
			label = new JLabel("Buscar");
			label.setBounds(10, 0, 50, 45);
			getContentPane().add(label);
			
				textBusca = new JTextField();
				textBusca.setBounds(70, 10, 300, 25);
				getContentPane().add(textBusca);
				
				btnBuscar = new JButton("Buscar");
				btnBuscar.setBounds(375, 10, 100, 25);
				getContentPane().add(btnBuscar);
				
			
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
			

							//GRID
							painel = new JPanel();
							getContentPane().add(painel);
							//Colunas da Grid
							String[] colunas = {"Código Origem", "Cidade Origem", "Código Destino", "Cidade Destino", "Distância"};
							
							Object [][] dados = {
								    //TODO: Dados para a Grid
								};
							
							JTable tabela = new JTable(dados,colunas);
							scrollpane = new JScrollPane(tabela);
							scrollpane.setBounds(10, 150, 600, 300);
							setLayout(null);
							scrollpane.setVisible(true);
							add(scrollpane);
							
			
						btnAdd = new JButton("+");
						btnAdd.setBounds(559, 120, 50, 25);
						getContentPane().add(btnAdd);
						
						btnSalvar = new JButton("SALVAR");
						btnSalvar.setBounds(385, 455, 110, 25);
						getContentPane().add(btnSalvar);
						
						btnProcessar = new JButton("PROCESSAR");
						btnProcessar.setBounds(500, 455, 110, 25);
						getContentPane().add(btnProcessar);
			
			
			
		}
		
		public static void main(String[] args) {
			
			new TelaBusca();
			
		}


}
