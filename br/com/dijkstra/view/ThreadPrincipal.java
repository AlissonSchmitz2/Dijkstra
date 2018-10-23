package br.com.dijkstra.view;

import java.io.File;
import java.util.ArrayList;

import br.com.dijkstra.algoritmo.Dijkstra;
import br.com.dijkstra.grafo.Grafo;
import br.com.dijkstra.lib.*;
import br.com.dijkstra.model.CaminhoManual;
import br.com.dijkstra.model.Config;
import br.com.dijkstra.model.DadosTxt;

public class ThreadPrincipal {
	private ManipularArquivo mA;
	private Config config;
	private DadosTxt dadosTxt = new DadosTxt();
	private ArrayList<CaminhoManual> listCM = new ArrayList<>();
	

	public ThreadPrincipal() {
		criarThread();
	}

	public void criarThread() {
		buscarDadosConfig();
		if (config == null) {
			return;
		}
		if (config.getCheck()) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						while (!Thread.currentThread().isInterrupted()) {
							System.out.println("TESTANDO PRIMEIRA THREAD...");

							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								throw e;
							}

							// Busca os dados para verificar se a checkBox foi desmarcada
							buscarDadosConfig();

							// Lista os arquivos na pasta principal, envia para o método para executar o
							// dijkstra
							try {
								File arquivosDiretorio = new File(config.getCaminhoPasta());
								File[] listaArquivos;
								listaArquivos = arquivosDiretorio.listFiles();

								// COLOCAR DENTRO DA THREAD
								for (int i = 0; i < listaArquivos.length; i++) {
									
									if (listaArquivos[i].toString().endsWith(".txt")) {
										new Thread(new Runnable() {

											@Override
											public void run() {
												
												for(int i = 0; i < listaArquivos.length; i++) {

												System.out.println("TESTANDO segunda THREAD...");
												
												listCM = mA.recuperarDados(listaArquivos[i].toString());
												arquivoNaPasta(listCM);
												
												}
												
											}
										}).start();

									}
								}
							} catch (Exception e) {
								// TODO: handle exception
							}

							if (!config.getCheck()) {
								break;
							}
						}
					} catch (Exception e) {
						System.out.println("THREAD INTERROMPIDA");
					}
					System.out.println("THREAD PAROU");
				}
			}, "Teste.arquivosPastaPrincipal").start();
		}
	}

	private void buscarDadosConfig() {
		mA = new ManipularArquivo();
		try {
			config = mA.recuperarDadosConfig(System.getProperty("user.home") + "\\dijkstra\\data\\config.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void arquivoNaPasta(ArrayList<CaminhoManual> listCM){
		
		// TODO: Executar Dijkstra no arquivo, o caminho está no argumento do parâmetro.
		Grafo grafo = new Grafo();
		System.out.println(listCM.size());
		
		//listCM = mA.recuperarDados(caminhoArquivoRota);

		grafo.montarGrafo(listCM);
		
		int qtdVertices = grafo.getVertices().size();
		int qtdAresta = grafo.getVertices().get(qtdVertices-1).getAresta().size();
		int codigoFinal = grafo.getVertices().get(qtdVertices-1).getAresta().get(qtdAresta-1).getCodDestino();
		
		int codigoInicio = listCM.get(0).getCodigoOrigem();
		System.out.println(qtdVertices);
		
		new Dijkstra(grafo,codigoInicio,codigoFinal,true);
		
		System.out.println("AUMENTA");
		
		/*if (true) {// Encontrou o menor caminho - pasta sucesso

			// cria um arquivo na pasta sucesso
			FileWriter arq = new FileWriter(new File(config.getCaminhoSucesso() + "\\nomeArquivoSucesso.txt"), true);// colocar
																														// nome
																														// do
																														// arquivo
																														// novo
			PrintWriter gravarArq = new PrintWriter(arq);

			gravarArq.println(dadosTxt.getDados());
			arq.close();

		}*/
		// Criando arquivo de erro, o else.
		// Erro caso a rota no arquivo esteja com problema - Cria o arquivo no
		// caminhoErro
		// FileWriter arq = new FileWriter(new File(config.getCaminhoErro() +
		// "\\erro.txt"), true);

	}

}