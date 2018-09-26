package br.com.dijkstra.view;

public class ThreadExemplo {

	public static void main(String[] args) {
		
		// Se Rota automatica ...
		if (true) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {

					try {				
						while (!Thread.currentThread().isInterrupted()) {
							
							// Se tem o arquivo no diretório padrão ...
							if (true) { // Substituir o true pelo path do arquivo.
								
							}
							
							// Aguarda um tempo para a CPU respirar.
							Thread.sleep(100);
						}
					}					
					catch (Exception e) {

					}
				}
			}).start();	
		}
	}

}
