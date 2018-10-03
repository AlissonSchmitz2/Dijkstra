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
	    
	    public Principal() {
	    	
	    	PopupMenu menu = new PopupMenu("Menu");
	        MenuItem menuConf = new MenuItem("Configuração");
	        MenuItem menuVisi = new MenuItem("Visível");
	        MenuItem quitItem = new MenuItem("Sair");
	        
	        menuConf.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//System.out.println("FORA");
					if (telaConfig == null) {
						telaConfig = new TelaConfiguracaoWindow();
						telaConfig.addWindowListener(new WindowAdapter() {
							public void windowClosing(WindowEvent evt) {
								telaConfig = null;
								//System.out.println("IF");
								menuVisi.setEnabled(true);
							}
						});	
						
					}
					else {		
						//System.out.println("ELSE");
						telaConfig.requestFocus();
						telaConfig.setFocusable(true);
						telaConfig.setExtendedState(JFrame.NORMAL);
						if(!telaConfig.isVisible()) {
							//System.out.println("IF 2");
							telaConfig = new TelaConfiguracaoWindow();
						}
					}
					menuVisi.setEnabled(false);
					
				}

			});
	        
	        //TODO: Ao salvar na janela de configuração, o menu visivel não é habilitado
	        
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

	        // Ação para clique com botão esquerdo.
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
	        
	        ti.displayMessage("Informações", "Clique com o botão direito do mouse neste ícone para alterar as configurações salvas.", TrayIcon.MessageType.INFO);
	        
	        //Colocar o caminho do config.check, if de teste.
	        /*boolean i=false;
	        if(i) {
	        	menuVisi.setEnabled(false);
	        }else {
	        	menuVisi.setEnabled(true);
	        }*/
	        diretorioVerificar();
	    }  
	    
	    private void diretorioVerificar() {
	    	File diretorio = new File(System.getProperty("user.home") + "\\dijkstra\\data");
	    	
	    	if(!diretorio.exists()) {
	    		JOptionPane.showMessageDialog(null, "Configurações dos arquivos não encontradas!\nFavor realizar a configuração na tela a seguir.");
	    		telaConfig = new TelaConfiguracaoWindow();
	    	}
	    }
	    
    public static void main(String[] args){
    	new Principal();
	}
}
