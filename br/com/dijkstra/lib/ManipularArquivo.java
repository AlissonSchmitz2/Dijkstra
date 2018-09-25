package br.com.dijkstra.lib;

import java.io.BufferedReader;
import java.io.File;

public class ManipularArquivo {

	private static String USUARIOS_PATH = System.getProperty("user.home") + "\\sistemaescolar\\data\\usuarios.txt";
	private static String CIDADES_PATH = System.getProperty("user.home") + "\\sistemaescolar\\data\\cidades.txt";
	private static String ALUNOS_PATH = System.getProperty("user.home") + "\\sistemaescolar\\data\\alunos.txt";
	
	private static String SEPARATOR = ";;;";
	
	private BufferedReader lerArq;

	public ManipularArquivo() {
		criarArquivosDados();
	}
	
	private void criarArquivosDados() {
		//Testa a existência do arquivo de dados "usuarios.txt", caso o mesmo não exista, cria o diretório
		File diretorio = new File(System.getProperty("user.home") + "\\sistemaescolar\\data");
		if (!diretorio.exists()) {
		   diretorio.mkdirs();
		}
	}

}
