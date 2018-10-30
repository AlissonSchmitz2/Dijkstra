package br.com.dijkstra.grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import br.com.dijkstra.model.CaminhoManual;

public class Grafo {

	private List<Vertice> vertice = new ArrayList<Vertice>();
	private final int OO = 9999;

	public List<Vertice> getVertices() {
		return vertice;
	}

	public void setVertices(Vertice novoVertice) {
		vertice.add(novoVertice);
	}

	ArrayList<CaminhoManual> listCM = null;

	// HashMap que guardar vertices para controle de exceções
	HashMap<Integer, String> map = new HashMap<Integer, String>();

	public void montarGrafo(ArrayList<CaminhoManual> listCM) throws Exception {
		this.listCM = listCM;

		for (int i = 0; i < listCM.size(); i++) {

			Aresta aresta = new Aresta();
			Vertice vertice = new Vertice();

			aresta.setCodOrigin(listCM.get(i).getCodigoOrigem());
			aresta.setCodDestino(listCM.get(i).getCodigoDestino());
			aresta.setCidade(listCM.get(i).getCidadeOrigem());
			aresta.setCidadeDestino(listCM.get(i).getCidadeDestino());
			aresta.setPesoAresta(listCM.get(i).getDistanciaKM());
			aresta.setDistancia(OO);

			// Tratamento de excessões;

			vertice.setAresta(aresta);
			vertice.setCodigo(listCM.get(i).getCodigoOrigem());
			vertice.setDistancia(OO);

			// TODO:Implementar lógica para vértices inseridos sem ordem;

			/*
			 * int aux = i; for(int j = aux;j<listCM.size();j++) {
			 * if(listCM.get(i).getCodigoOrigem() == listCM.get(j).getCodigoOrigem()) {
			 * Aresta aresta1 = new Aresta();
			 * aresta1.setCodOrigin(listCM.get(j).getCodigoOrigem());
			 * aresta1.setCodDestino(listCM.get(j).getCodigoDestino());
			 * aresta1.setPesoAresta(listCM.get(j).getDistanciaKM());
			 * aresta1.setDistancia(OO); vertice.setAresta(aresta1); ++i; } }
			 */

			if (i != listCM.size() - 1) {
				while (listCM.get(i + 1).getCodigoOrigem() == listCM.get(i).getCodigoOrigem()) {
					Aresta aresta1 = new Aresta();
					aresta1.setCodOrigin(listCM.get(i + 1).getCodigoOrigem());
					aresta1.setCodDestino(listCM.get(i + 1).getCodigoDestino());
					aresta1.setCidade(listCM.get(i + 1).getCidadeOrigem());
					aresta1.setCidadeDestino(listCM.get(i + 1).getCidadeDestino());
					aresta1.setPesoAresta(listCM.get(i + 1).getDistanciaKM());
					aresta1.setDistancia(OO);
					vertice.setAresta(aresta1);
					++i;
					if (i == listCM.size() - 1) {
						break;
					}
				}
			}

			this.setVertices(vertice);
		}

		inserirChaveValor(vertice);

	}

	public void inserirChaveValor(List<Vertice> v) throws Exception {
		for (int i = 0; i < v.size(); i++) {
			for (int j = 0; j < v.get(i).getAresta().size(); j++) {

				// Tratamento de exceções

				// Verifica se contem código em mais de uma cidade
				if (map.containsKey(v.get(i).getAresta().get(j).getCodOrigin())) {
					if (!(map.get(v.get(i).getAresta().get(j).getCodOrigin())
							.equals(v.get(i).getAresta().get(j).getCidade()))) {
						throw new Exception("Não foi possível percorrer a menor rota, o código "
								+ v.get(i).getAresta().get(j).getCodOrigin() + " contém"
								+ " duas ou mais cidades diferentes associada a ele.");
					}
				}

				if (map.containsKey(v.get(i).getAresta().get(j).getCodDestino())) {
					if (!(map.get(v.get(i).getAresta().get(j).getCodDestino())
							.equals(v.get(i).getAresta().get(j).getCidadeDestino()))) {
						throw new Exception("Não foi possível percorrer a menor rota, o código "
								+ v.get(i).getAresta().get(j).getCodDestino() + " contém"
								+ " duas ou mais cidades diferentes associada a ele.");
					}
				}

				// Verifica se contem cidade iguais com códigos distintos
				if (map.containsValue(v.get(i).getAresta().get(j).getCidade())) {
					Integer keyCidadeOrigem = getKeyByValue(map, v.get(i).getAresta().get(j).getCidade());

					if (!(keyCidadeOrigem).equals(v.get(i).getAresta().get(j).getCodOrigin())) {
						throw new Exception("Não foi possível percorrer a menor rota, a cidade "
								+ v.get(i).getAresta().get(j).getCidade() + " contém"
								+ " dois ou mais códigos diferentes associada a ela.");
					}
				}

				if (map.containsValue(v.get(i).getAresta().get(j).getCidadeDestino())) {
					Integer keyCidadeDestino = getKeyByValue(map, v.get(i).getAresta().get(j).getCidadeDestino());

					if (!(keyCidadeDestino).equals(v.get(i).getAresta().get(j).getCodDestino())) {
						throw new Exception("Não foi possível percorrer a menor rota, a cidade "
								+ v.get(i).getAresta().get(j).getCidadeDestino() + " contém"
								+ " dois ou mais códigos diferentes associada a ela.");
					}
				}

				map.put(v.get(i).getAresta().get(j).getCodOrigin(), v.get(i).getAresta().get(j).getCidade());
				map.put(v.get(i).getAresta().get(j).getCodDestino(), v.get(i).getAresta().get(j).getCidadeDestino());
			}
		}
	}

	// Recuperar a key a partir de um valor no HashMap.
	public static <T, E> T getKeyByValue(HashMap<T, E> map, E value) {

		for (Entry<T, E> entry : map.entrySet()) {

			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}

		return null;

	}
}