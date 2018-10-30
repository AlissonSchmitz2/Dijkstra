package br.com.dijkstra.draw;

import java.awt.Graphics;

import javax.swing.JFrame;

import br.com.dijkstra.grafo.Grafo;

public class DesenharGrafo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Grafo grafo = new Grafo();
	
	
	public DesenharGrafo(Grafo grafo) {
		this.grafo = grafo;
		setTitle("Grafo");
		setSize(595, 500);
		setResizable(false);
		setLayout(null);

		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public DesenharGrafo() {

	}
	
	public void paint(Graphics g) {
		int x = 40;
		int y = 40;
		
		
			g.drawOval(x, y, 20, 20);
			
	}

}
