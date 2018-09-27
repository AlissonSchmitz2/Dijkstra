package br.com.dijkstra.lib;

import java.io.BufferedReader;
import java.io.File;
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
		criarArquivosDados();
	}
	
	private void criarArquivosDados() {
		//Testa a existência do arquivo de dados "usuarios.txt", caso o mesmo não exista, cria o diretório
		File diretorio = new File(System.getProperty("user.home") + "\\dijkstra\\data");
		if (!diretorio.exists()) {
		   diretorio.mkdirs();
		}
	}
	
	public void inserirDado(Config config) {
		//config.setId(pegarProximoId("Config"));
		
		String novosDados = criarStringDados(config);

		inserirDadosNoArquivo("config", novosDados);
	}
	
	private String criarStringDados(Config config) {
		return config.getCaminhoPasta() + SEPARATOR + config.getCaminhoSucesso() + SEPARATOR + config.getCaminhoErro();
	}
	
	/*
	 * HELPERS
	 */
	//Método auxiliar responsável por gravar dos dados no arquivo
 	private void inserirDadosNoArquivo(String area, String dados) {
		try {
			FileWriter arq = new FileWriter(pegarDestinoArquivo(area), true);
			PrintWriter gravarArq = new PrintWriter(arq);
			gravarArq.println(dados);
			arq.close();
		} catch (IOException e) {
			System.err.printf("Não foi possível gravar o arquivo: %s.\n", e.getMessage());
		}
		
	}
 	
 	/*private Integer pegarProximoId(String area) {
		try {
			FileReader arq = new FileReader(pegarDestinoArquivo(area));
			lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			Integer maiorId = 0;
			
			while (linha != null) {
				String[] atributo = linha.split(SEPARATOR);
				
				Integer currentId = Integer.parseInt(atributo[0]);
				if (currentId.compareTo(maiorId) == 1) {
					maiorId = currentId;
				}
				
				linha = lerArq.readLine();
	        }
	        
	        lerArq.close();
	        
	        return maiorId + 1;
		} catch (IOException e) {
			return 1;
		}
	}
 	*/
 	//Retorna o caminho para o arquivo de dados
 		private String pegarDestinoArquivo(String area) {
 			switch (area) {
 				case "config":
 					return new File(CONFIG_PATH).getAbsolutePath();
 			}
 			
 			return null;
 		}

}
