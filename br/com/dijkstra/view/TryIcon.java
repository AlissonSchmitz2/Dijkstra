package br.com.dijkstra.view;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

	public class TryIcon {
	    public static JMenuItem quit;

	    public TryIcon() {
	    	
	        PopupMenu menu = new PopupMenu("Menu");
	        MenuItem menuConf = new MenuItem("Configuração");
	        MenuItem menuVisi = new MenuItem("Visível");
	        MenuItem quitItem = new MenuItem("Sair");
	        
	        
	        
	        menuConf.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new TelaConfiguracaoWindow().setVisible(true);;
				}
			});
	        
	        menuVisi.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new TelaBuscaWindow().setVisible(true);;
				}
			});
	        
	        quitItem.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	                System.exit(0);
	        }});
	        
	        menu.add(menuConf);
	        menu.addSeparator();
	        menu.add(menuVisi);
	        menu.addSeparator();
	        menu.add(quitItem);
	        
	        ImageIcon icon = new ImageIcon("br/com/dijkstra/icons/mundo.gif");
	        TrayIcon ti = new TrayIcon(icon.getImage(), "Dijsktra", menu);

	        // Ação para clique com botão esquerdo.
	        ti.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                JOptionPane.showMessageDialog(null, 
	                    "Alisson Schmitz\n" + 
	                    "Giovane Santiago\n" +
	                    "Vinnicius Mazzuchetti\n" +
	                    "Wilian Hendler\n", "Desenvolvimento",
	                    JOptionPane.INFORMATION_MESSAGE);
	            }
	        });
	               
	        SystemTray tray = SystemTray.getSystemTray();
	        try {
				tray.add(ti);
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }

	    public static void main(String[] args) {
	    	File diretorio = new File(System.getProperty("user.home") + "\\dijkstra\\data");
	    	
	    	if(!diretorio.exists()) {
	    		JOptionPane.showMessageDialog(null, "Configurações dos arquivos não encontradas, informe os caminhos para salvar os arquivos do Dijkstra.");
	    		new TelaConfiguracaoWindow().setVisible(true);;
	    	}else {
	    		new TryIcon();
	    	}
	    	
	    }   
	}

