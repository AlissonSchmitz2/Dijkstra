package br.com.dijkstra.model;

public class Config {
	private String caminhoPasta, caminhoSucesso, CaminhoErro;
	private Boolean check;
	
	
	public String getCaminhoPasta() {
		return caminhoPasta;
	}
	
	
	public void setCaminhoPasta(String caminhoPasta) {
		this.caminhoPasta = caminhoPasta;
	}
	
	
	public String getCaminhoSucesso() {
		return caminhoSucesso;
	}
	
	
	public void setCaminhoSucesso(String caminhoSucesso) {
		this.caminhoSucesso = caminhoSucesso;
	}
	
	
	public String getCaminhoErro() {
		return CaminhoErro;
	}
	
	
	public void setCaminhoErro(String caminhoErro) {
		CaminhoErro = caminhoErro;
	}
	
	
	public Boolean getCheck() {
		return check;
	}
	
	public void setCheck(Boolean check) {
		this.check = check;
	}
	
}
