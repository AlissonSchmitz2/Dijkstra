package br.com.dijkstra.model;

public class CaminhoManual {

	private int codigoOrigem;
	private int codigoDestino;
	private String cidadeOrigem;
	private String cidadeDestino;
	private float distanciaKM;
	
	public int getCodigoOrigem() {
		return codigoOrigem;
	}
	public void setCodigoOrigem(int codigoOrigem) {
		this.codigoOrigem = codigoOrigem;
	}
	
	public int getCodigoDestino() {
		return codigoDestino;
	}
	public void setCodigoDestino(int codigoDestino) {
		this.codigoDestino = codigoDestino;
	}
	
	public String getCidadeOrigem() {
		return cidadeOrigem;
	}
	public void setCidadeOrigem(String cidadeOrigem) {
		this.cidadeOrigem = cidadeOrigem;
	}
	
	public String getCidadeDestino() {
		return cidadeDestino;
	}
	public void setCidadeDestino(String cidadeDestino) {
		this.cidadeDestino = cidadeDestino;
	}
	
	public float getDistanciaKM() {
		return distanciaKM;
	}
	public void setDistanciaKM(float distanciaKM) {
		this.distanciaKM = distanciaKM;
	}	
	
}
