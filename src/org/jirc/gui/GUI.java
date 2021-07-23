
package org.jirc;


import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import org.jirc.net.ircConnection;
import org.jirc.gui.ServerWindow;
import org.jirc.util.ConfigReader;



/**
 * GUI.java provides every element that is need to handle and control JIRC.
 * GUI contains a main-function wich allows to run and initiate the program.
 * 
 * @version 0.0.1
 * @author jirc development team
 * @since 0.0.0
 */
public class GUI extends JFrame{
	
	/**
	 * mb is the MenuBar that contains the standard usability options.
	 */
	private JMenuBar mb;
	/**
	 * Menu for IRC Options
	 */
	private JMenu IRC;
	/**
	 * Menu for Client Options
	 */
	private JMenu Client;
	/**
	 * Menu for DCC Options
	 */
	private JMenu DCC;
	/**
	 * Menu for Help named "Question" because its title is a question mark.
	*/
	private JMenu Question;
	
	/**
	 * MenuItem that should connect.
	 */
	private JMenuItem Connect;
	/**
	 * Will call an configuration dialog.
	 */
	private JMenuItem IRCOptions;
	/**
	 * Shut down the client.
	 */
	private JMenuItem Exit;
	// Menu Client
	/**
	 * Configs for the client's look and feel
	 */
	private JMenuItem Colors;
	/**
	 * Configs for the client's look and feel
	 */
	private JMenuItem Fonts;
	/**
	 * Configs for the client's look and feel
	 */
	private JMenuItem Configs;
	// Menu DCC
	/**
	 * Send dialog for DCC
	 */
	private JMenuItem Send;
	/**
	 * Configuration Dialog for DCC
	 */
	private JMenuItem DCCOptions;
	// Menu Question
	/**
	 * Shows short information about jirc.
	 */
	private JMenuItem About;
	//////////////////////////
	
	/**
	 * Contains the ServerWindows, ChannelWindows and PrivateWindows
	 */
	private JTabbedPane tabBar;
	/**
	 * Contains a copy of the current frame (for easier handle)
	 */
	private JFrame thisFrame;
	/**
	 * Contains the ChannelWindows
	 */
	private Vector channels;
	/**
	 * Contains the ServerWindows
	 */
	private Vector <ServerWindow>servers;
	/**
	 * Contains the PrivateWindows
	 */
	private Vector privates;
	/**
	 * Contains the ircConnections
	 */
	private Vector <ircConnection>ircConnections;
	
	/**
	 * The menuBar mb is located her for easier handl in the LayoutManager
	 */
	private JDesktopPane menuPane;
	/**
	 * Connect Button
	 */
	private JButton conButton;
        
        /**
         * Reads the coniguration fie
         */
        private ConfigReader cr;

	
	/**
	 * The main function sets the LookAndFell to "WindowsLookAndFeel" and creates a new object mys of type GUI.
	 * 
	 */
	public static void main(String[] args) {
	
		try {                                        
		      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		    }
		catch(Exception e){;}
		
		//setGUI();
		GUI mys=new GUI();
		org.jirc.util.StringFunctions.printEcho("Mein Test!");
	}

	/**
	 * GUI is the one and only constructor at this time and is responsible for creating every object and vectors that are needed
	 * to handle graphical elemnts and data relying on the IRC Communication.
	 */
	public GUI(){
		
		mb = new JMenuBar();
	/* IRC Menu */
		IRC = new JMenu("IRC");
		Connect = new JMenuItem("Connect");
		IRCOptions = new JMenuItem("Options");
		Exit = new JMenuItem("Exit");
		
	/* Client Menu */
		Client = new JMenu("Client");
		Colors = new JMenuItem("Colors");
		Fonts = new JMenuItem("Fonts");
		Configs = new JMenuItem("Configs");
		
	/* DCC Menu */
		DCC = new JMenu("DCC");
		Send = new JMenuItem("Send");
		DCCOptions = new JMenuItem("DCCOptions");
		
	/* Question Menu */
		Question = new JMenu("?");
		About = new JMenuItem("About");
	
		
	/* Für Instanzierung von JDialog */
		thisFrame=this;
		
	/* Vectors initialisieren */
	 
		servers = new Vector<ServerWindow>();
		channels = new Vector();
		privates = new Vector();
		ircConnections = new Vector<ircConnection>();
		
	/* Buttons initialisieren */
		
		conButton = new JButton("Connect");
		
	/* Panes für North initialisieren */
		
		menuPane =  new JDesktopPane();
		menuPane.setLayout(new BorderLayout());
		menuPane.add(mb,BorderLayout.NORTH);
		menuPane.setBackground(new Color(234,232,227));
		menuPane.add(conButton,BorderLayout.WEST);
		
		
		tabBar=new JTabbedPane();
                
        /* Config Reader instanzieren */
                
                cr=new  ConfigReader("config.xml");
		
                while(cr.next()){
		
                    servers.add(new ServerWindow(cr.getServer()));
                    ircConnection con = new ircConnection(  cr.getAddress(),
                                                            cr.getPort(),
                                                            cr.getNick(),
                                                            cr.getHost(),
                                                            cr.getServer(),
                                                            cr.getRLName(),
                                                            (ServerWindow)servers.lastElement(),
                                                            channels,
                                                            privates,
                                                            tabBar);
                    
                    System.out.println("Size: " + servers.size());
                    new Thread(con).start();
		
                    if(con != null){
			
			tabBar.add((ServerWindow)servers.lastElement(),((ServerWindow)servers.lastElement()).getName());
			ircConnections.add(con);
			((ServerWindow)servers.lastElement()).setServer((ircConnection)ircConnections.lastElement());
                    }		
                }
                
                

		
                this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(menuPane,BorderLayout.NORTH);
		this.getContentPane().add(tabBar,BorderLayout.CENTER);
                
		
	// Menü wird aufgebaut
		
		mb.add(IRC);
		mb.add(Client);
		mb.add(DCC);
		mb.add(Question);
	
		IRC.add(Connect);
		IRC.add(IRCOptions);
		IRC.add(Exit);
		
		Client.add(Colors);
		Client.add(Fonts);
		Client.add(Configs);
		
		DCC.add(Send);
		DCC.add(DCCOptions);
		
		Question.add(About);
		
		/* this (Frame) wird angezeigt und gesetzt */
		
		this.setTitle("JIRC");
		this.pack();
		this.setSize(800,600);
		this.setBackground(new Color(204,204,204));
		this.setVisible(true);		
		
		this.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent w){
	        System.out.println("Fenster wird geschlossen");
                for(int i=0;i<servers.size();i++)
                    ((ircConnection)ircConnections.get(i)).sendMessage("quit");
	        System.exit(0);
	        };});
		
		
		About.addActionListener(new ActionListener(){
			
				public void actionPerformed(ActionEvent e){ 
					
						
					JLabel information=new JLabel();
					System.out.println("About");
					JDialog AboutInfo = new JDialog(thisFrame,"About",true);
					AboutInfo.getContentPane().add(information);
					AboutInfo.setSize(300,200);
					information.setHorizontalAlignment(SwingConstants.CENTER);
					information.setText("<html><center><b>JIRC</b> <br><br>http://jirc.karner.ws/<br> by Christian Karner, Brigitte Kupka<br> &copy; 2004,2005</center><</html>");
					AboutInfo.setResizable(false);
					AboutInfo.setVisible(true);
					
				}
			}
		);
                
                tabBar.addChangeListener(new ChangeListener() {
        
                    public void stateChanged(ChangeEvent evt) {
                        JTabbedPane pane = (JTabbedPane)evt.getSource();
                        int sel = pane.getSelectedIndex();
                        tabBar.setBackgroundAt(sel,null);
                        //tabBar.updateUI();
                    }
                });
		
		
		              
	}
	
	/**
	 * @deprecated
	 * Propably setGUI will be used to set the LookAndFeel depending on the used platform, but at the momet it's useless.
	 */
	public static void setGUI(){
		JFrame.setDefaultLookAndFeelDecorated(false);
	}
        
     
}