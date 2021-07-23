package org.jirc.net;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.jirc.gui.ServerWindow;
import org.jirc.gui.ChannelWindow;
import org.jirc.util.StringFunctions;

/**
 * ircConnection.java provides the sockets and multithreading that is needed for network communication.
 * 
 * @version 0.0.1
 * @author jirc development team
 * @since 0.0.0
 */

import java.net.*;
import java.io.*;
import java.util.Vector;
import javax.swing.JTabbedPane;
import java.awt.Color;



public class ircConnection implements Runnable {


	/**
	 * Useless, i don't know what it is for.
	 */
	int Counter=0;
	/**
	 * Received text
	 */
	String ServerText="";
	/**
	 * Communication socket.
	 */
	Socket cs = null;
	/**
	 * For outgoing data
	 */
	PrintWriter out=null;
	/**
	 * For incoming data
	 */
	BufferedReader in=null;
	
	/**
	 * Server adress
	 */
	private String address;  	// irc.icq.com
	/**
	 * The user's nick
	 */
	private String nick; 		// Taggy|Java
	/**
	 * Name of the local host
	 */
	private String host;		// taggy-pc
	/**
	 * Name of the server
	 */
	private String server;		// icq
	/**
	 * The user's real name
	 */
	private String rlName;		// Taggy's RLName
	/**
	 * Port to connect
	 */
	private int port;			// 6667 
	/**
	 * Contains entered channels
	 */
	private Vector <ChannelWindow>channels;	// enthält channel-Windows
	/**
	 * Contains private windows
	 */
	private Vector privates;	// enthält private-Windows
	/**
	 * Contains the serverWindow that is responsible for this connection
	 */
	private ServerWindow serverWindow;
        
        /**
         * Contains the JTabbedPane that is responsible for several windows
         */
        
        private JTabbedPane tabBar;
	
	/**
	 * Creates connection
	 * 
	 * @param address server address
	 * @param port server port
	 * @param nick the users's nick
	 * @param host the user's local host
	 * @param server the server's name
	 * @param rlName the user's real name 
	 * @param serverWindow realted server window
	 * @param channels related channels
	 * @param privates related privates
	 */
	public ircConnection(String address,int port,String nick,String host, String server,String rlName,ServerWindow serverWindow,Vector channels,Vector privates,JTabbedPane tabBar){
		
	/* Hier müssen die Parameter noch zugewiesen und überprüft werden!! */
		
		this.serverWindow=serverWindow;
		this.address=address;
		this.nick=nick;
		this.host=host;
		this.server=server;
		this.rlName=rlName;
		this.port=port;
                this.tabBar=tabBar;
                
                            
                
	/* Aufbau einer Verbindung mit dem IRC-Server */
		
		
		
	}
	 /* Momentan unnötigt */
	/**
	 * Not used at the moment
	 * @deprecated
	 */
	public void setServerWindow(ServerWindow serverWindow){
		
		this.serverWindow = serverWindow;
	}
	
	/**
	 * Communication in run() because of multithreading
	 */
	public void run(){
            
                channels = new Vector<ChannelWindow>();
		try{
			cs = new Socket(address,port);
			out=new PrintWriter(cs.getOutputStream(),true);
			in=new BufferedReader(new InputStreamReader(cs.getInputStream()));
			out.println("NICK " + nick);
			out.println("USER " + nick + " " + host+ " " + server + " " + rlName);
			
			
			String s = in.readLine();

			int i=0;
			while(s != null){
				
				
				/* Was hat es mit dem i auf sich? Anzahl der Zeilen!*/
				if(i==10){
					ServerText="";
					i=0;
				}
				ServerText = ServerText + "\n"+s;
				s=in.readLine();
				System.out.println(s);
				Thread.sleep(10);
				
				serverWindow.printMessage(s);
				
				//System.out.println(s.substring(0,3));
				if(s!=null){
					
					if(s.substring(0,4).compareTo("PING")==0 ){
						out.println("PONG "+s.substring(6,s.length()));
						ServerText = ServerText + "\n"+ "PONG:"+s.substring(6,s.length());  // historical issues?
						System.out.println("PONG "+s.substring(6,s.length()));
					}
                                        
                                        if(StringFunctions.analyzeServerInput(s) == "ChannelPrivMsg"){
                                            
                                            String channel=StringFunctions.getPrivMsgChannel(s);
                                            System.out.println("channel: " + channel);
                                            for(i=0;i<channels.size();i++){
                                                
                                                System.out.println("liste " +i+": " + ((ChannelWindow)channels.get(i)).getName());
                                                
                                                String chanComp=((ChannelWindow)channels.get(i)).getName();
                                                System.out.println("channel: "+channel+channel.length()+"\n Chancopm:"+chanComp+chanComp.length());
                                                if(channel.equalsIgnoreCase(chanComp)){
                                                    
                                                    System.out.println("channelwindow gefunden");
                                                    String [] sender_split=s.split("!",2);
                                                    String [] message_split=s.split(":",3);
                                                    
                                                    ((ChannelWindow)channels.get(i)).printMessage(message_split[2],(String)sender_split[0].substring(1));
                                                }
                                            }
                                        }
                                       if(StringFunctions.analyzeServerInput(s) == "userList"){
                                            
                                            String channel=StringFunctions.getUserListChannel(s);
                                            String channelUsers[]=StringFunctions.getChannelUsers(s);
                                            
                                            for(i=0;i<channels.size();i++){
                                                                                                                                         
                                                String chanComp=((ChannelWindow)channels.get(i)).getName();
                                                if(channel.equalsIgnoreCase(chanComp)){
                                       
                                                    ((ChannelWindow)channels.get(i)).setUserList(channelUsers);
                                                }
                                            }
                                        }
                                        if(StringFunctions.analyzeServerInput(s) == "userPart"){
                                            
                                            String channel=StringFunctions.getUserPartChannel(s);
                                            String channelPartUser=StringFunctions.getPartUser(s);
                                            System.out.println("part channel "+channel);
                                            for(i=0;i<channels.size();i++){
                                                                                                                                         
                                                String chanComp=((ChannelWindow)channels.get(i)).getName();
                                                if(channel.equalsIgnoreCase(chanComp)){
                                                    
                                                    System.out.println("Part channel gefunden1,user: "+channelPartUser);
                                                    ((ChannelWindow)channels.get(i)).removeFromUserList(channelPartUser);
                                                }
                                            }
                                        }
                                        if(StringFunctions.analyzeServerInput(s) == "userJoin"){
                                            
                                            String channel=StringFunctions.getUserJoinChannel(s);
                                            String channelJoinUser=StringFunctions.getJoinUser(s);
                                            System.out.println("join channel "+channel);
                                            for(i=0;i<channels.size();i++){
                                                                                                                                         
                                                String chanComp=((ChannelWindow)channels.get(i)).getName();
                                                if(channel.equalsIgnoreCase(chanComp)){
                                                    
                                                    System.out.println("Join channel gefunden,user: "+channelJoinUser);
                                                    ((ChannelWindow)channels.get(i)).addToUserList(channelJoinUser);
                                                }
                                            }
                                        }
				}
				i++;
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Function for outgoing data
	 * @param send_message data that is sent to the server.
	 */
	public void sendMessage(String send_message){
            
            if(StringFunctions.analyzeUserInput(send_message) == "join"){
                
                System.out.println("Es wird gejoint");
                String channel=StringFunctions.getJoinChannel(send_message);
                channels.add(new ChannelWindow(channel));
                tabBar.add((ChannelWindow)channels.lastElement(),((ChannelWindow)channels.lastElement()).getName());
                ((ChannelWindow)channels.lastElement()).setServer(this);
                send_message=send_message.substring(1);
                out.println(send_message);
            }
            else
		out.println(send_message);
	}
        
        public String getNick(){
            return nick;
        }
        
        public void removeChannel(ChannelWindow cw){
            
            tabBar.remove(cw);
            channels.remove(cw);
        }
        
        public void setHighlight(String channel){
            
            for(int i=0;i<tabBar.getTabCount();i++){
                                                                                                                                         
                String chanComp=tabBar.getTitleAt(i);
                if(channel.equalsIgnoreCase(chanComp)){
                                           
                    System.out.println("Highlight wird rot gesetzt!");
                    tabBar.setBackgroundAt(i,Color.RED);
                    tabBar.updateUI();
                }
            }
        }
}
