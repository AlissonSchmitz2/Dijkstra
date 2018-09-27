package br.com.dijkstra.lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import br.com.dijkstra.model.Config;

public class ManipularArquivo {

	private static String CONFIG_PATH = System.getProperty("user.home") + "\\dijkstra\\data\\config.txt";
	
	private static String SEPARATOR = ";;;";
	
	private BufferedReader lerArq;

	public ManipularArquivo() {
		criarArquivo();
	}
	
	private void criarArquivo() {
		//Testa a existência do arquivo de dados "config.txt", caso o mesmo não exista, cria o diretório
		File diretorio = new File(System.getProperty("user.home") + "\\dijkstra\\data");
			
			if (!diretorio.exists()) {
			   diretorio.mkdirs();
			}
	}
	
	private String criarStringDados(Config config) {
		return config.getCaminhoPasta() + SEPARATOR + config.getCaminhoSucesso() + SEPARATOR + config.getCaminhoErro();
	}
	
	public void inserirDado(Config config) {
		
		String novosDados = criarStringDados(config);

		inserirDadosNoArquivo("config", novosDados);
	}
	
	private void inserirDadosNoArquivo(String area, String dados) {

 		try {
			FileWriter arq = new FileWriter(pegarDestinoArquivo(area), true);
			PrintWriter gravarArq = new PrintWriter(arq);

			removerDadosDoArquivo(area);
		     gravarArq.println(dados);
			 arq.close();

		} catch (IOException e) {
			System.err.printf("Não foi possível gravar o arquivo: %s.\n", e.getMessage());
		}
 		
	}
	
	private void removerDadosDoArquivo(String area) {
 		try {
			FileReader arq = new FileReader(pegarDestinoArquivo(area));
			lerArq = new BufferedReader(arq);
			StringBuffer inputBuffer = new StringBuffer();
			
	        String inputStr = inputBuffer.toString();
	        
	        lerArq.close();
			
	        FileOutputStream fileOut = new FileOutputStream(pegarDestinoArquivo(area));
	        fileOut.write(inputStr.getBytes());
	        fileOut.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
 	}
	
 	//Retorna o caminho para o arquivo de dados
 		private String pegarDestinoArquivo(String area) {
 			switch (area) {
 				case "config":
 					return new File(CONFIG_PATH).getAbsolutePath();
 			}
 			
 			return null;
 		}

}
