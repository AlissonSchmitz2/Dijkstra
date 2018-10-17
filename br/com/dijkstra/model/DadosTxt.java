package br.com.dijkstra.model;

public class DadosTxt {
	private String dados = "";

	public DadosTxt() {
	}
	
	public DadosTxt(String dados) {
		this.dados = dados;
	}
	
	public String getDados() {
		return dados;
	}

	public void setDados(String dados) {
		this.dados += dados;
	}
}
