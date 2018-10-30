package br.com.dijkstra.algoritmo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.dijkstra.grafo.Aresta;
import br.com.dijkstra.grafo.Grafo;
import br.com.dijkstra.grafo.Vertice;
import br.com.dijkstra.model.Config;

public class Dijkstra {

	int verticeInicial;
	int arestaDestino;
	int verticeDestino;
	float distanciaVerticeAtual;
	float distanciaVerticeDestino;
	float pesoArestaAtual;
	int indiceDestinoFinal = 0;
	String destino = "";
	int cont = 0;
	Config config;

	// Lista do menor caminho
	List<Vertice> vertices = new ArrayList<Vertice>();

	// Lista de vertices não visitados
	List<Vertice> naoVisitado = new ArrayList<Vertice>();

	// Guarda vertice atual
	Vertice atual = new Vertice();

	static // Hash para guardar valores de cada vertice;
	HashMap<Integer, String> map = new HashMap<Integer, String>();

	int menorVertice = 9999;
	float menorPeso = 9999;
	int posMenorVertice = 0;
	int menorIndice = 0;
	
	private static Grafo grafo = null;
	private int codOrigin;
	private int codDestino;

	public Dijkstra() {
	}

	public Dijkstra(Grafo grafo, int codOrigin, int codDestino) throws Exception {
		Dijkstra.grafo = grafo;
		this.codOrigin = codOrigin;
		this.codDestino = codDestino;

		encontrarMenorCaminho();

	}

	public void encontrarMenorCaminho() throws Exception {

		// Inicializar rota inicial com distância zero

		verticeInicial = pegarIndiceVertice(codOrigin, grafo, false);
		System.out.println(verticeInicial);
		grafo.getVertices().get(0).setDistancia(0);

		for (int i = 0; i < grafo.getVertices().size(); i++) {
			// Adiciona todos os vertices aos não visitados
			this.naoVisitado.add(grafo.getVertices().get(i));
			this.vertices.add(grafo.getVertices().get(i));
		}

		while (!naoVisitado.isEmpty()) {
			cont++;

			// Encontra o indice da menor distancia de um vertice;
			for (int i = 0; i < naoVisitado.size(); i++) {
				// Verifica se o vertice foi visitado ou não;
				if (naoVisitado.get(i).getDistancia() < menorPeso) {
					menorVertice = naoVisitado.get(i).getCodigo();
					menorPeso = naoVisitado.get(i).getDistancia();
					posMenorVertice = i;
				}
			}

			// Atualiza indice de menorVertice de acordo com nova lista (vertices) a ser
			// usada;
			menorIndice = buscaMenorVertice(menorVertice);
			vertices.get(menorIndice).setVerticeVisitado(true);
			Vertice atual = vertices.get(menorIndice);
			distanciaVerticeAtual = vertices.get(menorIndice).getDistancia();

			for (int i = 0; i < vertices.get(menorIndice).getAresta().size(); i++) {
				arestaDestino = vertices.get(menorIndice).getAresta().get(i).getCodDestino();
				String cidadeDestino = vertices.get(menorIndice).getAresta().get(i).getCidadeDestino();
				verticeDestino = pegarIndiceVertice(arestaDestino, vertices);
				pesoArestaAtual = vertices.get(menorIndice).getAresta().get(i).getPesoAresta();

				// Verifica se códido destino é igual a arestaDestino para armazenar numa
				// variavel
				// para resultado final;

				if (arestaDestino == codDestino) {
					destino = cidadeDestino;
				}

				// Adiciona novo vertice, caso não encontre o codigo;
				if (verticeDestino == -1) {
					Aresta aDestino = new Aresta();
					Vertice vDestino = new Vertice();

					aDestino.setCidade(cidadeDestino);
					vDestino.setCodigo(arestaDestino);
					vDestino.setAresta(aDestino);
					vDestino.setVerticeVisitado(true);
					vDestino.setPai(atual);
					vDestino.setDistancia(distanciaVerticeAtual + pesoArestaAtual);
					vertices.add(vDestino);

					continue;
				}

				if (cont != vertices.size()) {
					distanciaVerticeDestino = vertices.get(verticeDestino).getDistancia();
				} else {
					break;
				}

				if ((distanciaVerticeAtual + pesoArestaAtual) < distanciaVerticeDestino) {
					vertices.get(verticeDestino).setDistancia(distanciaVerticeAtual + pesoArestaAtual);
					vertices.get(verticeDestino).setPai(atual);
					vertices.get(verticeDestino).getPai().setDistancia(atual.getDistancia());
				}
			}

			naoVisitado.remove(posMenorVertice);

			// Inicializa os valores iniciais para uma nova busca;
			menorVertice = 9999;
			menorPeso = 9999;

		}

		inserirChaveValor(vertices);
		mostrarMenorCaminho();

	}

	public int pegarIndiceVertice(int codigo, Grafo grafo, boolean verificaVerticeAresta) throws Exception {

		for (int i = 0; i < grafo.getVertices().size(); i++) {
			if (grafo.getVertices().get(i).getCodigo() == codigo) {
				return i;
			}
		}

		throw new Exception("Um dos indices não foi encontrado");
	}

	public int pegarIndiceVertice(int codigo, List<Vertice> grafo) {
		for (int i = 0; i < grafo.size(); i++) {
			if (grafo.get(i).getCodigo() == codigo) {
				return i;
			}
		}

		return -1;
	}

	public int buscaMenorVertice(int menorVertice) throws Exception {
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getCodigo() == menorVertice) {
				return i;
			}
		}

		throw new Exception("O código do vertice não foi encontrado");
	}

	public void inserirChaveValor(List<Vertice> v) {
		for (int i = 0; i < v.size(); i++) {
			map.put(v.get(i).getCodigo(), v.get(i).getAresta().get(0).getCidade());
		}

	}

	// Percorre menor caminho;
	public String mostrarMenorCaminho() {

		int codigoDestino = pegarIndiceVertice(codDestino, vertices);
		int codigoInicio = pegarIndiceVertice(codOrigin, vertices);

		float distancia = 0;
		String res = "";
		while (codigoDestino != codigoInicio) {
			distancia += vertices.get(codigoDestino).getDistancia() - vertices.get(codigoDestino).getPai().getDistancia();
			res = pegaCidadeReferenteAoCodigo(vertices.get(codigoDestino).getPai().getCodigo()) + ", " + res;
			codigoDestino = pegarIndiceVertice(vertices.get(codigoDestino).getPai().getCodigo(), vertices);
		}
		
		return "Menor Caminho: " + res + destino + "\nDistância total: " + distancia + "KM";

	}

	public static String pegaCidadeReferenteAoCodigo(int codigoCidade) {
		HashMap<Integer, String> mapa = map;
		return mapa.get(codigoCidade);
	}

}