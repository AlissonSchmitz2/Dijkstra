	package br.com.dijkstra.model;

public class Config {
	private String caminhoPasta, caminhoSucesso, CaminhoErro;
	private Boolean check;
	private Integer id;
	
	public Config() {
		
	}

	public Config(String caminhoPasta, String caminhoSucesso, String caminhoErro, Boolean check) {
		super();
		this.caminhoPasta = caminhoPasta;
		this.caminhoSucesso = caminhoSucesso;
		this.CaminhoErro = caminhoErro;
		this.check = check;
	}
	
	public Integer getId() {
		return id;
	}
 	public void setId(Integer id) {
		this.id = id;
	}

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
		this.CaminhoErro = caminhoErro;
	}
	
	
	public Boolean getCheck() {
		return check;
	}
	
	public void setCheck(Boolean check) {
		this.check = check;
	}
	
}
