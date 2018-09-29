package br.com.dijkstra.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CaminhoManualTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 2044640677156250114L;
	
	private List<CaminhoManual> dados = new ArrayList<>();
	private String[] colunas = {"Código Origem", "Cidade Origem", "Código Destino", "Cidade Destino", "Distância"};
	
	//Retorna a quantidade de colunas da JTable.
	public int getColumnCount() {
		return colunas.length;
	}

	//Retorna a quantidade de linhas da JTable.
	public int getRowCount() {
		return dados.size();
	}
	
	//Retornar valor a partir da linha e coluna.
	public Object getValueAt(int linhaIndex, int colunaIndex) {
		
		switch(colunaIndex) {
			case 0: 
				return dados.get(linhaIndex).getCodigoOrigem();
			case 1:
				return dados.get(linhaIndex).getCidadeOrigem();
			case 2:
				return dados.get(linhaIndex).getCodigoDestino();
			case 3:
				return dados.get(linhaIndex).getCidadeDestino();
			case 4:
				return dados.get(linhaIndex).getDistanciaKM();
			
		}
		
		return null;
	}	
	
	public String getColumnName(int column) {
		return colunas[column];
	}
	
	//Adiciona dados a tabela.
	public void addRow(CaminhoManual CM) {
		this.dados.add(CM);
		
		//Atualiza a tabela quando há mudança nos dados.
		this.fireTableDataChanged();
	}
	
	//Limpa os dados da tabela.
	public void limpar() {
		dados.clear();
		this.fireTableDataChanged();
	}

}
