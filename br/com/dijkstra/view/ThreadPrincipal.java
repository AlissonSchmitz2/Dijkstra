package br.com.dijkstra.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import br.com.dijkstra.lib.*;
import br.com.dijkstra.model.Config;
import br.com.dijkstra.model.DadosTxt;


public class ThreadPrincipal {
	private ManipularArquivo mA;
	private Config config;
	private DadosTxt dadosTxt = new DadosTxt();

	public ThreadPrincipal() {
		criarThread();
	}
	
	public void criarThread(){
		
		buscarDadosConfig();
		
		if(config == null) {
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
										Thread.sleep(300);
									} catch (InterruptedException e) {
										throw e;
									}
									
									//Busca os dados para verificar se a checkBox foi desmarcada
									buscarDadosConfig();
																	
									//Lista os arquivos na pasta principal, envia para o método para executar o dijkstra
									try {
									File arquivosDiretorio = new File(config.getCaminhoPasta());
									File[] listaArquivos;
									listaArquivos = arquivosDiretorio.listFiles();
									
									for(int i=0; i<listaArquivos.length; i++) {
										arquivoNaPasta(listaArquivos[i].toString());
									}
									}catch (Exception e) {
										// TODO: handle exception
									}
									/*
									//VERIFICA SE FOI COLOCADO ALGUM ARQUIVO NA PASTA PRINCIPAL
									for(String arquivos : arquivosDiretorio.list()) {
										if(arquivos.endsWith(".txt")) {
											//TODO: TEM ARQUIVO NA PASTA
										}
									}*/
								
									if(!config.getCheck()) {
									break;
									}
								}
							}					
							catch (Exception e) {
								System.out.println("THREAD INTERROMPIDA");
							}
							System.out.println("THREAD PAROU");
						}
			},"Teste.arquivosPastaPrincipal").start();	
		}
	}
	
	private void buscarDadosConfig() {
		mA = new ManipularArquivo();
		try{
			config = mA.recuperarDadosConfig(System.getProperty("user.home") + "\\dijkstra\\data\\config.txt");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void arquivoNaPasta(String caminhoArquivoRota) throws IOException{

		//TODO: Executar Dijkstra no arquivo, o caminho está no argumento do parâmetro.
		//lendo os dados e armazenando no model - dados do arquivo txt encontrado na pasta principal
		dadosTxt = mA.buscarDadosTxt(caminhoArquivoRota);
		
		System.out.println("veioooo");

		if(true) {//Encontrou o menor caminho - pasta sucesso
		
		//cria um arquivo na pasta sucesso
		FileWriter arq = new FileWriter(new File(config.getCaminhoSucesso() + "\\nomeArquivoSucesso.txt"), true);//colocar nome do arquivo novo
		PrintWriter gravarArq = new PrintWriter(arq);
		
		gravarArq.println(dadosTxt.getDados());
		arq.close();
		
		}
		//Criando arquivo de erro, o else.
		//Erro caso a rota no arquivo esteja com problema - Cria o arquivo no caminhoErro
		//FileWriter arq = new FileWriter(new File(config.getCaminhoErro() + "\\erro.txt"), true);
		
	}
	
}