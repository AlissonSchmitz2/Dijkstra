package br.com.dijkstra.view;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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

import br.com.dijkstra.grafo.Grafo;
import br.com.dijkstra.lib.ManipularArquivo;
import br.com.dijkstra.model.CaminhoManual;
import br.com.dijkstra.model.CaminhoManualTableModel;

public class TelaBuscaWindow extends JFrame {
	private static final long serialVersionUID = 8509005971460712336L;

	private JTextField textBusca, textCodOrigem, textCidadeOrigem, textCodDestino, textCidadeDestino, textKm;
	private JButton btnBuscar, btnAdd, btnSalvar, btnProcessar;
	private JLabel label;

	private JScrollPane scrollpane;
	private TableModel tableModel = new CaminhoManualTableModel();
	private ArrayList<CaminhoManual> listCM = new ArrayList<>();
	Grafo grafo;
	ManipularArquivo mA = new ManipularArquivo();
	private boolean importouTXT = false;
	private HashMap<Integer, String> caminhosAdicionados = new HashMap<>();

	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {

				if (textBusca.isFocusOwner()) {
					btnBuscar.doClick();
				} else if (textCidadeDestino.isFocusOwner() || textCodDestino.isFocusOwner()
						|| textCidadeOrigem.isFocusOwner() || textCodOrigem.isFocusOwner() || textKm.isFocusOwner()) {
					btnAdd.doClick();
				}
			}
		}
	};

	public TelaBuscaWindow() {
		setTitle("Dijsktra - Busca por melhor caminho");
		setSize(635, 525);
		setResizable(false);
		setLayout(null);

		setLocationRelativeTo(null);
		criarComponentes();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		Image imagemTitulo = Toolkit.getDefaultToolkit()
				.getImage(getClass().getResource("/br/com/dijkstra/icons/busca.png"));
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

		// Campos de ORIGEM
		label = new JLabel("Código: ");
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

		// Campos de DESTINO
		label = new JLabel("Código: ");
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

		// Tabela de dados.
		JTable tabela = new JTable();
		tabela.setModel(tableModel);
		// Scroll para tabela.
		scrollpane = new JScrollPane(tabela);
		scrollpane.setBounds(10, 150, 600, 300);
		scrollpane.setVisible(true);
		add(scrollpane);

		btnAdd = new JButton(new AbstractAction("+") {
			private static final long serialVersionUID = 2349363006048570163L;

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String validacao = validacoesAdicao();

				if (validacao.equals("")) {

					caminhosAdicionados.put(Integer.parseInt(textCodOrigem.getText()), textCidadeOrigem.getText());
					caminhosAdicionados.put(Integer.parseInt(textCodDestino.getText()), textCidadeDestino.getText());

					CaminhoManual CM = new CaminhoManual();
					CM.setCidadeDestino(textCidadeDestino.getText());
					CM.setCidadeOrigem(textCidadeOrigem.getText());
					CM.setCodigoDestino(Integer.parseInt(textCodDestino.getText()));
					CM.setCodigoOrigem(Integer.parseInt(textCodOrigem.getText()));
					CM.setDistanciaKM(Float.parseFloat(textKm.getText()));

					// Adição do objeto na lista para depois adicionar no TXT.
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

				if ("".equals(textBusca.getText())) {
					textBusca.setText(fileChooser());
				}

				try {

					if (textBusca.getText().equals("") == false) {
						importouTXT = true;
						listCM.clear();
						listCM = new ArrayList<>();

						listCM = mA.recuperarDados(textBusca.getText());
						((CaminhoManualTableModel) tableModel).limpar();

						for (int i = 0; i < listCM.size(); i++) {
							((CaminhoManualTableModel) tableModel).addRow(listCM.get(i));
						}

					}

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(rootPane, "O arquivo não foi encontrado ou é inválido.", "",
							JOptionPane.ERROR_MESSAGE, null);
				}

			}
		});
		btnBuscar.setBounds(375, 10, 100, 25);
		getContentPane().add(btnBuscar);
		btnBuscar.addKeyListener(acao);

		btnSalvar = new JButton(new AbstractAction("SALVAR") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				if (listCM.isEmpty()) {
					JOptionPane.showMessageDialog(rootPane,
							"Não há nada para ser salvo ou nenhuma mudança foi realizada no arquivo!", "",
							JOptionPane.ERROR_MESSAGE, null);
				} else {

					String nomeArquivo;

					// Salva no mesmo arquivo oque foi adicionado após a importação.
					if (importouTXT) {

						nomeArquivo = textBusca.getText();
						mA.inserirDado(listCM, nomeArquivo, true);
						JOptionPane.showMessageDialog(null, "Arquivo salvo com sucesso!");
						listCM.clear();

						int limpar = JOptionPane.showConfirmDialog(null, "Deseja limpar a tabela?");

						if (limpar == 0) {
							((CaminhoManualTableModel) tableModel).limpar();
						}

					} else {

						nomeArquivo = JOptionPane.showInputDialog(null, "Digite o nome do arquivo a ser salvo:");

						if (mA.verificarNomeTxt(nomeArquivo + ".txt")) {

							do {
								JOptionPane.showMessageDialog(rootPane,
										"Esse nome já existe no diretório. Por favor, digite outro nome.", "",
										JOptionPane.ERROR_MESSAGE, null);
								nomeArquivo = JOptionPane.showInputDialog(null,
										"Digite o nome do arquivo a ser salvo:");
							} while (mA.verificarNomeTxt(nomeArquivo + ".txt"));

						}

						if (nomeArquivo != null) {
							mA.inserirDado(listCM, "\\" + nomeArquivo + ".txt", false);
							JOptionPane.showMessageDialog(null, "Arquivo salvo com sucesso!");

							int limpar = JOptionPane.showConfirmDialog(null, "Deseja limpar a tabela?");

							if (limpar == 0) {
								((CaminhoManualTableModel) tableModel).limpar();
							}
						}
					}

				}
			}
		});
		btnSalvar.setBounds(385, 455, 110, 25);
		getContentPane().add(btnSalvar);

		btnProcessar = new JButton(new AbstractAction("PROCESSAR") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (listCM.isEmpty()) {
				JOptionPane.showMessageDialog(rootPane, "Cadastre ao menos um caminho!", "",
							JOptionPane.ERROR_MESSAGE, null);
				} else {
					new BuscaCidadeWindow(listCM).setVisible(true);
				}
			}
		});

		btnProcessar.setBounds(500, 455, 110, 25);
		getContentPane().add(btnProcessar);
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

		if ("".equals(textCidadeDestino.getText()) || "".equals(textCidadeOrigem.getText())
				|| "".equals(textCodDestino.getText()) || "".equals(textCodOrigem.getText())
				|| "".equals(textKm.getText())) {
			return "Todos os campos devem ser preenchidos!";
		} else if (textCodDestino.getText().equals(textCodOrigem.getText())) {
			return "O código de destino não pode ser igual ao código de origem!";
		} else if (textCidadeDestino.getText().equals(textCidadeOrigem.getText())) {
			return "O nome da cidade de destino não pode ser igual ao da cidade de origem!";
		} else if (verificarLetras(textCodOrigem.getText()) || verificarLetras(textCodDestino.getText())
				|| verificarLetras(textKm.getText())) {
			return "Os campos 'Código' ou 'KM' não podem conter letras!";
		} else if (textKm.getText().contains(",") || textCodDestino.getText().contains(",")
				|| textCodOrigem.getText().contains(",")) {
			return "Os campos 'Código' ou 'KM' não podem conter vírgulas";
		} else if (textCodDestino.getText().contains(".") || textCodOrigem.getText().contains(".")) {
			return "Os campos 'Código' não podem conter pontos";
		}

		// Validação para evitar que o mesmo código possua cidades diferentes.
		Integer codOrigem = Integer.parseInt(textCodOrigem.getText());
		Integer codDestino = Integer.parseInt(textCodDestino.getText());
		String cidadeOrigem = textCidadeOrigem.getText();
		String cidadeDestino = textCidadeDestino.getText();

		if (caminhosAdicionados.containsKey(codOrigem)) {

			if (!((caminhosAdicionados.get(codOrigem)).toString()).equals(cidadeOrigem)) {
				return "O código " + codOrigem + " já contém a cidade " + caminhosAdicionados.get(codOrigem)
						+ " associada a ele.";
			}
		}

		if (caminhosAdicionados.containsKey(codDestino)) {

			if (!((caminhosAdicionados.get(codDestino)).toString()).equals(cidadeDestino)) {
				return "O código " + codDestino + " já contém a cidade " + caminhosAdicionados.get(codDestino)
						+ " associada a ele.";
			}
		}

		// Validação para evitar que a mesma cidade possua códigos diferentes.
		if (caminhosAdicionados.containsValue(cidadeOrigem)) {

			Integer keyCidadeOrigem = getKeyByValue(caminhosAdicionados, cidadeOrigem);
			if (!codOrigem.equals(keyCidadeOrigem)) {
				return "A cidade " + cidadeOrigem + " já contém o código " + keyCidadeOrigem + " associado a ela.";
			}
		}

		if (caminhosAdicionados.containsValue(cidadeDestino)) {

			Integer keyCidadeDestino = getKeyByValue(caminhosAdicionados, cidadeDestino);
			if (!codDestino.equals(keyCidadeDestino)) {
				return "A cidade " + cidadeDestino + " já contém o código " + keyCidadeDestino + " associado a ela.";
			}
		}

		// Validação para evitar a adição de linhas iguais na table.
		if (verificarLinhaDuplicada()) {
			return "As informações que você está tentando adicionar já existem na tabela.";
		}

		// Nenhum erro encontrado.
		return "";
	}

	// Verificar a adição de linhas identicas a table.
	public boolean verificarLinhaDuplicada() {

		String linhaExistente;
		String novaLinha;

		novaLinha = textCodOrigem.getText() + textCidadeOrigem.getText() + textCodDestino.getText()
				+ textCidadeDestino.getText();

		for (int i = 0; i < listCM.size(); i++) {
			linhaExistente = listCM.get(i).getCodigoOrigem() + listCM.get(i).getCidadeOrigem()
					+ listCM.get(i).getCodigoDestino() + listCM.get(i).getCidadeDestino();
			if (linhaExistente.equals(novaLinha)) {
				return true;
			}
		}

		return false;
	}

	// Recuperar a key a partir de um valor no HashMap.
	public static <T, E> T getKeyByValue(HashMap<T, E> map, E value) {

		for (Entry<T, E> entry : map.entrySet()) {

			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}

		return null;
	}

	// Verifica se uma determinada string possui letras.
	private boolean verificarLetras(String texto) {

		for (int i = 0; i < texto.length(); i++) {
			if (Character.isLetter(texto.charAt(i))) {
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

	@Override
	public void setFocusable(boolean focusable) {
		super.setFocusable(focusable);
		textBusca.requestFocus();
	}

}
