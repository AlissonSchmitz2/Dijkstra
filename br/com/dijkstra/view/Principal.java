package br.com.dijkstra.view;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
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
	    	new ThreadPrincipal();
	    	
			if (diretorio.exists()) {
				buscarDadosConfig();
			}

	    	PopupMenu menu = new PopupMenu("Menu");
	        MenuItem menuConf = new MenuItem("Configura��o");
	        MenuItem menuVisi = new MenuItem("Vis�vel");
	        MenuItem quitItem = new MenuItem("Sair");

	        //Verifica a configura��o previamente estabelecida no txt para habilitar ou n�o o menu vis�vel.
	        if(config == null) {
	        	System.out.println("PRIMEIRA VEZ");
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
					
					//Verifica se a tela de configura��o foi fechada utilizando o 'X'.
					telaConfig.addWindowListener(new WindowAdapter() {
						public void windowClosed(WindowEvent evt) {
							
							buscarDadosConfig();
							menuVisi.setEnabled(config == null || !config.getCheck());
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
	        
			ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/dijkstra/icons/mundo.gif")));

	        TrayIcon ti = new TrayIcon(icon.getImage(), "Dijsktra", menu); 

	        // A��o para clique com bot�o esquerdo.
	        ti.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                JOptionPane.showMessageDialog(null, 
	                    "Alisson Schmitz\n" + 
	                    "Giovane Santiago\n" +
	                    "Victor Cechinel\n" +
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
	        
	        ti.displayMessage("Informa��es", "O dijkstra est� sendo executado em segundo plano, clique aqui para alterar configura��es", TrayIcon.MessageType.INFO);
	        
	        diretorioVerificar();
	    }
	    
	    private void diretorioVerificar() {
	    	File diretorio = new File(System.getProperty("user.home") + "\\dijkstra\\data\\config.txt");
	    	
	    	if(!diretorio.exists()) {
	    		JOptionPane.showMessageDialog(null, "Configura��es dos arquivos n�o encontradas!\nFavor realizar a configura��o na tela a seguir.");
	    		telaConfig = new TelaConfiguracaoWindow();
	    		//executar.setAtivo(false);
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

    	//Para teste:
    	//new TelaBuscaWindow().setVisible(true);
	}
}
