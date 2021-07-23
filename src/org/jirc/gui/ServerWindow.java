package org.jirc.gui;

/**
 * ServerWindow.java provides the input field and a copy of the realted ircConnection for channel communication.
 * 
 * @version 0.0.1
 * @author jirc development team
 * @since 0.0.0
 */

import javax.swing.*;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.StringBuffer;

import org.jirc.net.ircConnection;
import org.jirc.util.StringFunctions;
import org.jirc.gui.outputHyperlinkListener;



public class ServerWindow extends JDesktopPane implements HyperlinkListener{

	private JEditorPane output;
	private JScrollPane outputScrollPane;
	private JTextField input;
	private String name;
	private StringBuffer data;
	private ircConnection serverConnection;
	
	/**
	 * The constructor creates the neededw graphical elemnts for input and output.
	 * @param name Name of the Server
	 */
	public ServerWindow(String name){
		
		this.name=name;
                data=new StringBuffer("");
		output=new JEditorPane();
		input=new JTextField();
		outputScrollPane = new JScrollPane(output);
	
               
		this.setLayout(new BorderLayout());
		this.add(outputScrollPane,BorderLayout.CENTER);
		this.add(input,BorderLayout.SOUTH);
                output.setContentType("text/html");
                output.addHyperlinkListener(this);
                output.setEditable(false);
		
		input.addKeyListener(new KeyListener(){
			
				public void keyReleased(KeyEvent e){
						
					if(e.getKeyChar() == KeyEvent.VK_ENTER){
						
						String send_message=input.getText();
						serverConnection.sendMessage(send_message);
						printMessage(send_message);
						input.setText("");
					}
					
				}
				public void keyPressed(KeyEvent e){;}
				public void keyTyped(KeyEvent e){;}
			}
		);
	}
	
	/**
	 * Returns the server's name.
	 */
	public String getName(){
		
		return name;
	}
	
	/**
	 * Prints new incoming data to the outputArea
	 * @param message - Input from the ircConnection
	 */
	public void printMessage(String message){

		data.append(message+"<br>");
		output.setText(data.toString());
                
                outputScrollPane.getVerticalScrollBar().setValue( outputScrollPane.getVerticalScrollBar().getMaximum() );
                output.setVisible(true);
                
	}
	/**
	 * It's reommended to set a ircConnection immediately after creating ServerWindow, otherwise the program won't work.
	 * @param serverConnection ircConnection that is contained in GUI
	 */
	public void setServer(ircConnection serverConnection){
		
		this.serverConnection=serverConnection;
	}
	
	
/*
	void autoScroll() {
		int max = outputScrollPane.getJScrollBar().getMaximum();
		scrollPane.getJScrollBar().setValue(max);
		
		outputScrollPane.getS
	}*/
	
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
}
