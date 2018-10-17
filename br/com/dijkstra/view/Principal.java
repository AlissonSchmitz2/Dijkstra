package br.com.dijkstra.view;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import br.com.dijkstra.lib.ManipularArquivo;
import br.com.dijkstra.model.Config;

	public class Principal {
	    public static JMenuItem quit;
	    private Config config;
	    private ManipularArquivo mA;
	    TelaConfiguracaoWindow telaConfig = null;
	    TelaBuscaWindow telaBusca = null;
	    File diretorio = new File(System.getProperty("user.home") + "\\dijkstra\\data");
	    
	    public Principal() {
	    	
			if (diretorio.exists()) {
				buscarDadosConfig();
			}
	    	
	    	PopupMenu menu = new PopupMenu("Menu");
	        MenuItem menuConf = new MenuItem("Configura��o");
	        MenuItem menuVisi = new MenuItem("Vis�vel");
	        MenuItem quitItem = new MenuItem("Sair");

	        //Verifica a configura��o previamente estabelecida no txt para habilitar ou n�o o menu vis�vel.
	        if(config == null) {
	        	System.out.println("Opa, config nulo cara ");
	        }else if(config.getCheck() && config != null) {
	        	menuVisi.setEnabled(false);
	        }
	        
	        //MENU CONFIGURA��O
	        menuConf.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					menuVisi.setEnabled(false);
					
					if (telaConfig == null) {
						telaConfig = new TelaConfiguracaoWindow();
					}
					else {
						telaConfig.requestFocus();
						telaConfig.setFocusable(true);
						telaConfig.setExtendedState(JFrame.NORMAL);
						if(!telaConfig.isVisible()) {
							telaConfig.setVisible(true);
						}
					}
					menuVisi.setEnabled(false);
					
					 //Verifica se a tela de configura��o foi fechada utilizando o 'X'.
					telaConfig.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent evt) {
							if(config == null) {
								menuVisi.setEnabled(true);
								return;
							}else
							
							if(config.getCheck()) {
								menuVisi.setEnabled(false);
							} else {
								menuVisi.setEnabled(true);
							}
							
						}
					});
					
					//Verifica se a tela de configura��o foi fechada utilizando o comando 'Dispose'.
					telaConfig.addWindowListener(new WindowAdapter() {
						public void windowClosed(WindowEvent evt) {
							
							if(config == null) {
								menuVisi.setEnabled(true);
								return;
							}else
							
							if(config.getCheck()) {
								menuVisi.setEnabled(false);
							} else {
								menuVisi.setEnabled(true);
							}
						}
					});	
					
				}

			});
	        
	        //MENU VIS�VEL
	        menuVisi.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (telaBusca == null) {
						telaBusca = new TelaBuscaWindow();
						telaBusca.addWindowListener(new WindowAdapter() {
							public void windowClosing(WindowEvent evt) {
								telaBusca = null;
								menuConf.setEnabled(true);  
							}
						});	
						
					}else {
						telaBusca.requestFocus();
						telaBusca.setFocusable(true);
						telaBusca.setExtendedState(JFrame.NORMAL);
					}
					menuConf.setEnabled(false);  
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

	        // A��o para clique com bot�o esquerdo.
	        ti.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                JOptionPane.showMessageDialog(null, 
	                    "Alisson Schmitz\n" + 
	                    "Giovane Santiago\n" +
	                    "Vinnicius Mazzuchetti\n" +
	                    "Wilian Hendler\n", "Desenvolvimento",
	                    JOptionPane.NO_OPTION);
	            }
	        });
	               
	        SystemTray tray = SystemTray.getSystemTray();
	        try {
				tray.add(ti);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
	        
	        ti.displayMessage("Informa��es", "Clique com o bot�o direito do mouse neste �cone para alterar as configura��es salvas.", TrayIcon.MessageType.INFO);
	        
	        diretorioVerificar();
	    }
	    
	    private void diretorioVerificar() {
	    	File diretorio = new File(System.getProperty("user.home") + "\\dijkstra\\data");
	    	
	    	if(!diretorio.exists()) {
	    		JOptionPane.showMessageDialog(null, "Configura��es dos arquivos n�o encontradas!\nFavor realizar a configura��o na tela a seguir.");
	    		telaConfig = new TelaConfiguracaoWindow();
	    	}
	    }
	    
	    private void buscarDadosConfig() {
			mA = new ManipularArquivo();
			try{
				config = mA.recuperarDadosConfig(System.getProperty("user.home") + "\\dijkstra\\data\\config.txt");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	    
    public static void main(String[] args){
    	new Principal();
    	
    	new ThreadPrincipal();
	}
}
