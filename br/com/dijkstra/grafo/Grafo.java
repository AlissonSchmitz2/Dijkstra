package br.com.dijkstra.grafo;

import java.util.ArrayList;
import java.util.List;

import br.com.dijkstra.model.CaminhoManual;

public class Grafo {

	private List<Vertice> vertice = new ArrayList<Vertice>();
	private final int OO = Integer.MAX_VALUE;
	
	public List<Vertice> getVertices() {
		return vertice;
	}

	public void setVertices(Vertice novoVertice) {
		vertice.add(novoVertice);
	}
	
	public void montarGrafo(ArrayList<CaminhoManual> listCM){
		for (int i = 0; i < listCM.size(); i++) {

			Aresta aresta = new Aresta();
			Vertice vertice = new Vertice();

			aresta.setCodOrigin(listCM.get(i).getCodigoOrigem());
			aresta.setCodDestino(listCM.get(i).getCodigoDestino());
			aresta.setCidade(listCM.get(i).getCidadeOrigem());
			aresta.setCidadeDestino(listCM.get(i).getCidadeDestino());
			aresta.setPesoAresta(listCM.get(i).getDistanciaKM());
			aresta.setDistancia(OO);
			
			//Tratamento de excessões;
			
			
			vertice.setAresta(aresta);
			vertice.setCodigo(listCM.get(i).getCodigoOrigem());
			vertice.setDistancia(OO);
			
			//TODO:Implementar lógica para vértices inseridos sem ordem;
			
			/*int aux = i;
			for(int j = aux;j<listCM.size();j++) {
				if(listCM.get(i).getCodigoOrigem() == listCM.get(j).getCodigoOrigem()) {
					Aresta aresta1 = new Aresta();
					aresta1.setCodOrigin(listCM.get(j).getCodigoOrigem());
					aresta1.setCodDestino(listCM.get(j).getCodigoDestino());
					aresta1.setPesoAresta(listCM.get(j).getDistanciaKM());
					aresta1.setDistancia(OO);
					vertice.setAresta(aresta1);
					++i;
				}
			}*/
			
			if (i != listCM.size() - 1) {
				while (listCM.get(i + 1).getCodigoOrigem() == listCM.get(i).getCodigoOrigem())
				{
					Aresta aresta1 = new Aresta();
					aresta1.setCodOrigin(listCM.get(i + 1).getCodigoOrigem());
					aresta1.setCodDestino(listCM.get(i + 1).getCodigoDestino());
					aresta1.setCidade(listCM.get(i).getCidadeOrigem());
					aresta1.setCidadeDestino(listCM.get(i).getCidadeDestino());
					aresta1.setPesoAresta(listCM.get(i + 1).getDistanciaKM());
					aresta1.setDistancia(OO);
					vertice.setAresta(aresta1);
					++i;
					if(i == listCM.size() -1 ) {
						break;
					}
				}
			}
			
			this.setVertices(vertice);
		}
	}

}
