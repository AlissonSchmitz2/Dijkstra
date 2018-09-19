
import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.*;
import javax.swing.*;


	public class TryIcon {
	    public static JMenuItem quit;
	    
	    public TryIcon() {
	    	
	        PopupMenu menu = new PopupMenu("Tray Icon Menu");
	        menu.add(new MenuItem("Configuração"));
	        menu.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new TelaConfiguracao().setVisible(true);;
				}
			});
	        
	        menu.addSeparator();
	        menu.add(new MenuItem("Visível"));
	        menu.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new TelaBusca().setVisible(true);;
				}
			});
	        menu.addSeparator();

	        
	        MenuItem quitItem = new MenuItem("Sair");
	        quitItem.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	                System.exit(0);
	            }});
	        menu.add(quitItem);
	        
	        // onde estiver esta classe.
	        ImageIcon icon = new ImageIcon("icon.png");
	        TrayIcon ti = new TrayIcon(icon.getImage(), "Dijsktra", menu);

	        // Ação para clique com botão esquerdo.
	        ti.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                JOptionPane.showMessageDialog(null, 
	                    "JDIC Tray Icon API Test!", "About",
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
	        new TryIcon();
	    }   
	}

