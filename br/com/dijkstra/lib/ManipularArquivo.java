package br.com.dijkstra.lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import br.com.dijkstra.model.CaminhoManual;
import br.com.dijkstra.model.Config;

public class ManipularArquivo {

	private static String CONFIG_PATH = System.getProperty("user.home") + "\\dijkstra\\data\\config.txt";
	private static String ROTASMANUAIS_PATH = System.getProperty("user.home") + "\\dijkstra\\data";
	
	private static String SEPARATOR = ";;;";
	
	private boolean importouTXT = false;
	
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
		return config.getCaminhoPasta() + SEPARATOR + config.getCaminhoSucesso() + SEPARATOR + config.getCaminhoErro() + SEPARATOR + config.getCheck();
	}
	
	private String criarStringDados(CaminhoManual CM) {
		return CM.getCodigoOrigem() + SEPARATOR + CM.getCidadeOrigem() + SEPARATOR + CM.getCodigoDestino() + SEPARATOR + CM.getCidadeDestino() + SEPARATOR +
			   CM.getDistanciaKM();
	}
	
	public void inserirDado(Config config) {
		
		String novosDados = criarStringDados(config);

		inserirDadosNoArquivo("config", novosDados);
	}
	
	public void inserirDado(ArrayList<CaminhoManual> CM, String nomeArquivo, boolean importouTXT) {
		
		this.importouTXT = importouTXT;
		
		if(this.importouTXT) {
			String[] dados = new String[CM.size()];
			
	        for(int i = 0; i < CM.size(); i++) {
	        	dados[i] = criarStringDados(CM.get(i));
	        }
	        
	        inserirDadosNoArquivo(nomeArquivo, dados, null);
		} else {
		
		String[] dados = new String[CM.size()];
		
        for(int i = 0; i < CM.size(); i++) {
        	dados[i] = criarStringDados(CM.get(i));
        }		

		inserirDadosNoArquivo("rotasManuais", dados, nomeArquivo);
		}
	}
	
	private void inserirDadosNoArquivo(String area, String dados) {

 		try {
			FileWriter arq = new FileWriter(pegarDestinoArquivo(area, null), true);
			PrintWriter gravarArq = new PrintWriter(arq);

			removerDadosDoArquivo(area);
		     gravarArq.println(dados);
			 arq.close();

		} catch (IOException e) {
			System.err.printf("Não foi possível gravar o arquivo: %s.\n", e.getMessage());
		}
 		
	}
	
	//Inserir dados nos txts das rotas manuais.
	private void inserirDadosNoArquivo(String area, String[] dados, String nomeArquivo) {

 		try {
 			
 			FileWriter arq;
 			
 			if(this.importouTXT) { 				
 				arq = new FileWriter(area, true);
 				this.importouTXT = false;
 			} else {
 				arq = new FileWriter(pegarDestinoArquivo(area, nomeArquivo), true);
 			}
			
			PrintWriter gravarArq = new PrintWriter(arq);

			for(int i = 0; i < dados.length; i++) {
				gravarArq.println(dados[i]);
			}
		    
			arq.close();

		} catch (IOException e) {
			System.err.printf("Não foi possível gravar o arquivo: %s.\n", e.getMessage());
		}
 		
	}
	
	public ArrayList<CaminhoManual> recuperarDados(String destinoTXT){
		
		try {			
		FileReader arq = new FileReader(destinoTXT);
		lerArq = new BufferedReader(arq);
		String linha = lerArq.readLine();
		
		ArrayList<CaminhoManual> arrayDados = new ArrayList<>();
		
		while (linha != null) {						
			
			String[] atributo = linha.split(SEPARATOR);
						
			arrayDados.add(criarCaminhoManualApartirAtributos(atributo));
			
			linha = lerArq.readLine();
		}
		
		return arrayDados;
		
		} catch(IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		
		return null;		
	}
	
	private CaminhoManual criarCaminhoManualApartirAtributos(String[] atributos) {
		CaminhoManual CM = new CaminhoManual();
		
		CM.setCodigoOrigem(Integer.parseInt(atributos[0]));
		CM.setCidadeOrigem(atributos[1]);
		CM.setCodigoDestino(Integer.parseInt(atributos[2]));
		CM.setCidadeDestino(atributos[3]);
		CM.setDistanciaKM(Float.parseFloat(atributos[4]));
		
		return CM;
	}
	
	//Verifica se o nome do arquivo a ser salvo já existe no diretório.
	public boolean verificarNomeTxt(String nomeArquivo) {
		
		File diretorio = new File(ROTASMANUAIS_PATH);
		File[] arquivos = diretorio.listFiles();
		
		for(int i = 0; i < arquivos.length; i++) {
			
			if(arquivos[i].getName().equals(nomeArquivo)) {
				return true;
			}			
		}
		
		return false;
	}
	
	private void removerDadosDoArquivo(String area) {
 		try {
			FileReader arq = new FileReader(pegarDestinoArquivo(area, null));
			lerArq = new BufferedReader(arq);
			StringBuffer inputBuffer = new StringBuffer();
			
	        String inputStr = inputBuffer.toString();
	        
	        lerArq.close();
			
	        FileOutputStream fileOut = new FileOutputStream(pegarDestinoArquivo(area, null));
	        fileOut.write(inputStr.getBytes());
	        fileOut.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
 	}
	
 	//Retorna o caminho para o arquivo de dados
 	private String pegarDestinoArquivo(String area, String nomeArquivoRotas) {
 		switch (area) {
 			case "config":
 				return new File(CONFIG_PATH).getAbsolutePath();
 			case "rotasManuais":
 				return new File(ROTASMANUAIS_PATH + nomeArquivoRotas).getAbsolutePath();
 		}
 		
 		return null;
 	}

}
