package br.com.dijkstra.grafo;

public class Aresta {
	private int codOrigin;
	private int codDestino;
	private String cidadeOrigem;
	private String cidadeDestino;
	private float pesoAresta;
	private float distancia;
	
	public int getCodOrigin() {
		return codOrigin;
	}
	
	public void setCodOrigin(int codOrigin) {
		this.codOrigin = codOrigin;
	}
	
	public int getCodDestino() {
		return codDestino;
	}
	
	public void setCodDestino(int codDestino) {
		this.codDestino = codDestino;
	}
	
	public float getPesoAresta() {
		return pesoAresta;
	}

	public void setPesoAresta(float pesoAresta) {
		this.pesoAresta = pesoAresta;
	}

	public float getDistancia() {
		return distancia;
	}

	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}
	
	public void setCidade(String cidadeOrigem) {
		this.cidadeOrigem = cidadeOrigem;	
	} 
	
	public String getCidade() {
		return cidadeOrigem;
	}
	
	public void setCidadeDestino(String cidadeDestino) {
		this.cidadeDestino = cidadeDestino;	
	} 
	
	public String getCidadeDestino() {
		return cidadeDestino;
	}
	
}
