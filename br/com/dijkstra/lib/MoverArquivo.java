package br.com.dijkstra.lib;

import java.io.File;

public class MoverArquivo{
	
	public MoverArquivo() {
	}
	
	public boolean moveFile(String pastaPrincipal, String nomeArquivo, String pastaCopia) {
		
		boolean moveu = false;
		
		File arquivo = new File(pastaPrincipal + "\\" +nomeArquivo);
		if (arquivo.exists()) {
			File diretorioDestino = new File(pastaCopia);
			moveu = arquivo.renameTo(new File(diretorioDestino, arquivo.getName()));
		}
		
		return moveu;
	}
}