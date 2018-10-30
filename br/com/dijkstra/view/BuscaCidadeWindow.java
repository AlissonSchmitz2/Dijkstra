package br.com.dijkstra.view;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import br.com.dijkstra.algoritmo.Dijkstra;
import br.com.dijkstra.draw.DesenharGrafo;
import br.com.dijkstra.grafo.Grafo;
import br.com.dijkstra.lib.ManipularArquivo;
import br.com.dijkstra.lib.MoverArquivo;
import br.com.dijkstra.model.CaminhoManual;
import br.com.dijkstra.model.Config;

public class BuscaCidadeWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JComboBox<String> cbxCidadeOrigem;
	private JComboBox<String> cbxCidadeDestino;
	private JButton btnCaminho;
	private JLabel label;
	Grafo grafo;
	ArrayList<CaminhoManual> listCM = new ArrayList<>();
	ManipularArquivo mA = new ManipularArquivo();
	Config config;
	String destinoTxt = "";

	public BuscaCidadeWindow() {

	}

	public BuscaCidadeWindow(ArrayList<CaminhoManual> listCM, String destinoTxt) {
		this.listCM = listCM;
		this.destinoTxt = destinoTxt;
		setTitle("Buscar cidades");
		setSize(295, 200);
		setResizable(false);
		setLayout(null);

		setLocationRelativeTo(null);
		criarComponentes();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		Image imagemTitulo = Toolkit.getDefaultToolkit()
				.getImage(getClass().getResource("/br/com/dijkstra/icons/busca.png"));
		this.setIconImage(imagemTitulo);
		buscarDadosConfig();
	}

	public void criarComponentes() {
		label = new JLabel("Cidade Origem: ");
		label.setBounds(10, 10, 120, 45);
		getContentPane().add(label);

		cbxCidadeOrigem = new JComboBox<String>();
		cbxCidadeOrigem.addItem("-Selecione-");
		cbxCidadeOrigem.setBounds(105, 20, 150, 25);
		getContentPane().add(cbxCidadeOrigem);
		opcoesCidadeOrigem(listCM).forEach(cidadeOrigem -> cbxCidadeOrigem.addItem(cidadeOrigem));

		label = new JLabel("Cidade Destino: ");
		label.setBounds(10, 50, 120, 45);
		getContentPane().add(label);

		cbxCidadeDestino = new JComboBox<String>();
		cbxCidadeDestino.addItem("-Selecione-");
		cbxCidadeDestino.setBounds(105, 60, 150, 25);
		getContentPane().add(cbxCidadeDestino);
		opcoesCidadeDestino(listCM).forEach(cidadeDestino -> cbxCidadeDestino.addItem(cidadeDestino));

		btnCaminho = new JButton(new AbstractAction("Iniciar caminho") {
			private static final long serialVersionUID = 1L;
			Dijkstra djk;
			MoverArquivo cop = new MoverArquivo();

			@Override
			public void actionPerformed(ActionEvent e) {
				if (verificarCamposObrigatorios()) {
					JOptionPane.showMessageDialog(rootPane, "Informe todos os campos para iniciar o caminho!", "",
							JOptionPane.ERROR_MESSAGE, null);
				} else {
					int codCidadeOrigem = pegarCodigoPorNomeCidade(cbxCidadeOrigem.getSelectedItem().toString());
					int codCidadeDestino = pegarCodigoPorNomeCidade(cbxCidadeDestino.getSelectedItem().toString());

					if (codCidadeOrigem < 0 || codCidadeDestino < 0) {
						JOptionPane.showMessageDialog(null, "Código informado não pode ser negativo!");
					}

					try {
						dispose();
						grafo = new Grafo();
						grafo.montarGrafo(listCM);
						djk = new Dijkstra(grafo, codCidadeOrigem, codCidadeDestino);
						String mensagem = djk.mostrarMenorCaminho();
						JOptionPane.showMessageDialog(null, mensagem);
						mA.inserirCaminhoNoArquivo(destinoTxt, mensagem, true);
						cop.moveFile(destinoTxt, config.getCaminhoSucesso());
						setVisible(false);
					} catch (Exception e1) {
						String mensagem = e1.getMessage();
						if(mensagem == null) {
							mensagem = "Não foi possivel chegar ao seu destino";
						}
						JOptionPane.showMessageDialog(rootPane, mensagem, "", JOptionPane.ERROR_MESSAGE, null);
						mA.inserirCaminhoNoArquivo(destinoTxt, mensagem, true);
						cop.moveFile(destinoTxt, config.getCaminhoErro());
						setVisible(false);
					}
				}
			}
		});

		btnCaminho.setBounds(105, 120, 150, 25);
		getContentPane().add(btnCaminho);

	}

	public int pegarCodigoPorNomeCidade(String cidade) {
		for (int i = 0; i < listCM.size(); i++) {

			if (listCM.get(i).getCidadeOrigem().equals(cidade)) {
				return listCM.get(i).getCodigoOrigem();
			} else if (listCM.get(i).getCidadeDestino().equals(cidade)) {
				return listCM.get(i).getCodigoDestino();
			}

		}
		return -1;
	}

	private List<String> opcoesCidadeOrigem(List<CaminhoManual> cidades) {
		return cidades.stream().map(cidade -> cidade.getCidadeOrigem()).distinct().collect(Collectors.toList());
	}

	private List<String> opcoesCidadeDestino(List<CaminhoManual> cidades) {
		return cidades.stream().map(cidade -> cidade.getCidadeDestino()).distinct().collect(Collectors.toList());
	}

	public boolean verificarCamposObrigatorios() {
		if (cbxCidadeOrigem.getSelectedItem().equals("-Selecione-")
				|| cbxCidadeDestino.getSelectedItem().equals("-Selecione-")) {
			return true;
		}									
		return false;
	}

	private void buscarDadosConfig() {
		mA = new ManipularArquivo();
		try {
			config = mA.recuperarDadosConfig(System.getProperty("user.home") + "\\dijkstra\\data\\config.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
