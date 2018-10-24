package br.com.dijkstra.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	DadosTxt dadosTxt = new DadosTxt();
	private ArrayList<CaminhoManual> listCM = new ArrayList<>();
	private int i;

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
							System.out.println("TESTANDO THREAD...");

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
								final File[] listaArquivos;
								listaArquivos = arquivosDiretorio.listFiles();
								
								// COLOCAR DENTRO DA THREAD

								for (i = 0; i < listaArquivos.length; i++) {

									if (listaArquivos[i].toString().endsWith(".txt")) {

										// System.out.println("TESTANDO segunda THREAD..." +listaArquivos[i].toString()
										// );

										listCM = mA.recuperarDados(listaArquivos[i].toString());

										try {
											arquivoNaPasta(listCM, listaArquivos[i].getName());
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
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

	private void arquivoNaPasta(ArrayList<CaminhoManual> listCM, final String nomeArquivo) throws IOException {

		//Executando Dijkstra.
		Grafo grafo = new Grafo();
		System.out.println(listCM.size());

		grafo.montarGrafo(listCM);

		int qtdVertices = grafo.getVertices().size();
		int qtdAresta = grafo.getVertices().get(qtdVertices - 1).getAresta().size();
		int codigoFinal = grafo.getVertices().get(qtdVertices - 1).getAresta().get(qtdAresta - 1).getCodDestino();

		int codigoInicio = listCM.get(0).getCodigoOrigem();
		System.out.println(qtdVertices);

		Dijkstra djk = new Dijkstra(grafo, codigoInicio, codigoFinal, true);
		String mensagem = djk.mostrarMenorCaminho(true);

		System.out.println("DIJKSTRA - MOVEU");
		System.out.println(nomeArquivo);
		
		//ESCREVENDO NO ARQUIVO
		FileWriter arq = new FileWriter(new File(config.getCaminhoPasta() + "\\" + nomeArquivo), true);
		PrintWriter gravarArq = new PrintWriter(arq);
		
		gravarArq.println(mensagem); 
		arq.close();

		//MOVENDO ARQUIVO
		moveArquivo cop = new moveArquivo();
		System.out.println(nomeArquivo);
		cop.moveFile(config.getCaminhoPasta(), nomeArquivo, config.getCaminhoSucesso());

		
		 //if (true) {// Encontrou o menor caminho - pasta sucesso
		 
		 // cria um arquivo na pasta sucesso 

		  
		 //}

	}

}