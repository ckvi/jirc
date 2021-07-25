package org.jirc.gui;

import org.jirc.util.StringFunctions;
import org.jirc.net.ircConnection;
import org.jirc.gui.FocusJTextField;

import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.KeyboardFocusManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;






/*
 * Created on 08.01.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Taggy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


/**
 * ChannelWindows.java provides the input field and functions for channel communication.
 * 
 * @version 0.0.1
 * @author jirc development team
 * @since 0.0.0
 */
public class ChannelWindow extends JDesktopPane implements HyperlinkListener{

	/**
	 * Left area for communication content, right area for a list of names that are linked to the channel.
	 */
	private JSplitPane splitPane;
	/**
	 * User that entered the channel
	 */
	private Vector <String>users;
	/**
	 * Name of the channel
	 */
	private String name;
        
        /**
         * Area for channel communication
         */
        private JEditorPane output;
        
        /**
         * Contains the channel data
         */
        private StringBuffer data;
	
        /**
         * Input Field for user Chat Input
         */
        private FocusJTextField input;
        
        /**
         * ircConnection for irc Communication over ChannelWindow
         */        
        private ircConnection serverConnection;
        
        
        /**
         * Dimension to resize the SpliPane correctly after a frame resize.
         * private Dimension compSize;
         */
        private Dimension compSize;
        
        /**
         * JScrollPane for output Area (JEditorPane)
         */
        private JScrollPane outputScrollPane;
        
        private ChannelWindow myCW;
        
        
        /**
         * Contains the channel's users
         */
        private JList userList;
        
	/**
	 * ChannelWindow is always created with a given channel-name that shows and identifies the channel.
	 * @param name Name of the channel
	 */
	public ChannelWindow(String name){

		output=new JEditorPane();
                myCW=this;
		this.name=name;
		users=new Vector<String>();
		userList=new JList(users);
                
                data=new StringBuffer("");
	
	
		output.setMinimumSize(new Dimension(150,150));
                output.setEditable(false);
                output.setContentType("text/html");
                output.addHyperlinkListener(this);
                outputScrollPane=new JScrollPane(output);
                outputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(outputScrollPane);
		splitPane.setRightComponent(userList);
		splitPane.setOneTouchExpandable(true);
		
		splitPane.setMinimumSize(new Dimension(300,300));
		splitPane.setPreferredSize(new Dimension(400, 200));
		splitPane.setVisible(true);
		
		
		
		this.setLayout(new BorderLayout());
		this.add(splitPane,BorderLayout.CENTER);
		splitPane.setDividerLocation(700);
                
                input=new FocusJTextField();
                
                this.add(input,BorderLayout.SOUTH);
                
                
                
                input.addKeyListener(new KeyListener(){
			
				public void keyReleased(KeyEvent e){
						
					if(e.getKeyChar() == KeyEvent.VK_ENTER){
						
                                                String send_message=input.getText();
						
                                                if(StringFunctions.analyzeUserInput(send_message)=="part"){
                                                    
                                                    serverConnection.sendMessage("part "+ getName());
                                                    serverConnection.removeChannel(myCW);
                                                }
                                                else{
                                                    serverConnection.sendMessage("privmsg " + getName() + "  :" + send_message);
                                                    printMessage(send_message,serverConnection.getNick());
                                                    input.setText("");
                                                }
					}
                                        if(e.getKeyCode() == KeyEvent.VK_TAB){
                                            
                                            System.out.println("Tab wurde gedrückt!");
                                            String send_message=input.getText();
                                            String user="";
                                            if(send_message.length() != 0){
                                                int blank_index=send_message.lastIndexOf(" ");
                                                if(blank_index == -1){
                                                    blank_index=0;
                                                    user=send_message.substring(blank_index);
                                                }
                                                else{
                                                    user=send_message.substring(blank_index+1);
                                                }
                                                
                                                for(int i=0;i<users.size();i++){
                                                
                                                    if((users.get(i)).matches(Pattern.quote(user) + ".*$")){
                                                    
                                                        input.setText(send_message.substring(0,blank_index)+" "+users.get(i));
                                                        System.out.println("NickCompletion erfolgreich!");
                                                    }
                                                }
                                            }
                                       }
					
				}
                                public void keyPressed(KeyEvent e){
                                  
                                    if (e.getKeyCode() == KeyEvent.VK_TAB){
                                        if (e.isShiftDown())
                                            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
                                        else
                                            if (input.getText() != "")
                                                input.requestFocusInWindow();
                                            else
                                                 KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                                    }
                                }
    
				public void keyTyped(KeyEvent e){;}
			}
		);
                
                this.addComponentListener(new ComponentListener(){
                
                    public void componentResized(ComponentEvent e){
                        compSize=getSize();
                        splitPane.setDividerLocation(compSize.width-150);
                    }
                    public void componentHidden(ComponentEvent e){;}
                    public void componentMoved(ComponentEvent e){;}
                    public void componentShown(ComponentEvent e){;}
                }
                );
                
              
	
        }
	/**
	 * returns the name of the channel
	 */
	public String getName(){
		
		return name;
	}
        
        public void printMessage(String message){

		data.append(message+"<br>");
		output.setText(data.toString());
                Point point = new Point( 0, (int)(output.getSize().getHeight()) );
                outputScrollPane.getViewport().setViewPosition( point ); 
                output.repaint();
                output.validate();
                
        }
        
        public void printMessage(String message,String sender){
                
                message=message.replaceAll("<","&lt;");
                message=message.replaceAll(">","&gt;");
                
                if(!StringFunctions.checkUrl(message)){
		
                    data.append("&lt;"+sender+"&gt;"+message+"<br>");
                     
                }
                else{
                    
                    int url_start = message.indexOf("http://");
                    int url_stop  = message.indexOf(" ",url_start);
                    
                    if(url_stop == -1)
                        url_stop=message.length();
                        
                    data.append("&lt;" 
                                + sender 
                                + "&gt;"
                                + message.substring(0,url_start)
                                + "<a href=\""
                                + message.substring(url_start,url_stop)
                                + "\">"
                                + message.substring(url_start,url_stop)
                                + "</a> "
                                + message.substring(url_stop)
                                + "<br>");
                   
                    
                }
                output.setText(data.toString());
                outputScrollPane.getVerticalScrollBar().setValue( outputScrollPane.getVerticalScrollBar().getMaximum() );
                output.setVisible(true);
                
                if(message.indexOf(serverConnection.getNick()) != -1){
                    
                    serverConnection.setHighlight(this.getName());                    
                }
       } 
        
        public void setServer(ircConnection serverConnection){
		
		this.serverConnection=serverConnection;
	}
        
        public void hyperlinkUpdate(HyperlinkEvent event) {
            if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    Runtime.getRuntime().exec( "rundll32 url.dll,FileProtocolHandler " + event.getURL() );
                     //urlField.setText(event.getURL().toExternalForm());
                } catch(IOException ioe) {
                    System.out.println("Can't follow link to " + event.getURL().toExternalForm() + ": " + ioe);
                }
            }
        }
        
        public void setUserList(String channelUsers[]){
            
            for(int i=0;i<channelUsers.length;i++){
                
                System.out.println("setUserList: "+channelUsers[i]);
                users.add(i,channelUsers[i]);
            }
            userList=new JList(users);         
        }
        
        public void removeFromUserList(String channelPartUser){
            
            for(int i=0;i<users.size();i++){
                
                System.out.println("userliste "+i+" "+Pattern.quote((String)users.get(i)));
                
                if(((String)users.get(i)).matches(".?"+ Pattern.quote(channelPartUser))){
                    System.out.println("user gefunden und removed");
                    users.remove(i);
                }
                
                // quoten!!!
            }
            userList.setListData(users);  
            this.updateUI();
        }
        
        public void addToUserList(String channelJoinUser){
            
            if(channelJoinUser.compareTo(serverConnection.getNick())!=0){
            
                users.add(channelJoinUser);
                userList.setListData(users);
                splitPane.setRightComponent(userList);
                compSize=getSize();
                splitPane.setDividerLocation(compSize.width-150);
                this.updateUI();
            }
        }
}

